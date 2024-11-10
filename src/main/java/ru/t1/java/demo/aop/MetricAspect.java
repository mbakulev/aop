package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.TimeoutMonitoringDto;
import ru.t1.java.demo.kafka.KafkaProducerWithHeaders;

import java.util.UUID;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MetricAspect {
    private final KafkaProducerWithHeaders kafkaProducerWithHeaders;
    @Value("${t1.maxExecutionTime}")
    private long timeLimit;
    @Value("${t1.kafka.topic.t1_demo_metrics}")
    private String topic;

    @Around("@annotation(MetricTimeoutMonitoring)")
    public Object logException(ProceedingJoinPoint joinPoint) throws Throwable {
        long beforeTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            long afterTime = System.currentTimeMillis();
            long actualExecTime = afterTime - beforeTime;

            if (actualExecTime > timeLimit) {
                System.out.println("METHOD RABOTAET DOLGO");
                TimeoutMonitoringDto timeoutMonitoringDto = new TimeoutMonitoringDto();
                timeoutMonitoringDto.setExecutionTime(actualExecTime);
                timeoutMonitoringDto.setMethodName(joinPoint.getSignature().getName());
                timeoutMonitoringDto.setParameters(joinPoint.getArgs());

                kafkaProducerWithHeaders.sendMessageWithHeaders(
                        topic,
                        UUID.randomUUID().toString(),
                        "METRICS",
                        timeoutMonitoringDto);
            }
        }

        return result;
    }
}
