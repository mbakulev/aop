package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.kafka.KafkaProducerWithHeaders;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.service.impl.DataErrorLogServiceImpl;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogDataSourceErrorAspect {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaProducerWithHeaders kafkaProducerWithHeaders;
    @Value("${t1.kafka.topic.t1_demo_metrics}")
    private String topic;

    @Pointcut("within(ru.t1.java.demo.*)")
    public void loggingMethods() {
    }

    @AfterThrowing(pointcut = "@annotation(LogDataError)", throwing = "ex")
    public void logExceptionAnnotation(JoinPoint joinPoint, Exception ex) {
        DataSourceErrorLog dataSourceErrorLog = DataSourceErrorLog.builder()
                .methodName(joinPoint.getSignature().getName())
                .stackTrace(Arrays.toString(ex.getStackTrace()))
                .message(ex.getMessage())
                .build();


        kafkaProducerWithHeaders.sendMessageWithHeaders(
                topic,
                UUID.randomUUID().toString(),
                "DATA_SOURCE",
                dataSourceErrorLog);
//            kafkaTemplate.send("t1_demo_metrics", UUID.randomUUID().toString(), clientDto);
//            kafkaTemplate.flush();

        // dataSourceErrorLogService.writeErrorMessage(joinPoint, ex);
    }
}
