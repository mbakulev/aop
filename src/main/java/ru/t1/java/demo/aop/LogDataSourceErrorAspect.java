package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.ClientDto;
import ru.t1.java.demo.kafka.KafkaClientProducer;
import ru.t1.java.demo.kafka.KafkaProducerService;
import ru.t1.java.demo.kafka.KafkaProducerWithHeaders;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.service.impl.DataErrorLogServiceImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogDataSourceErrorAspect {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaClientProducer kafkaClientProducer;
    private final KafkaProducerService kafkaProducerService;
    private final KafkaProducerWithHeaders kafkaProducerWithHeaders;
    @Autowired
    private DataErrorLogServiceImpl dataSourceErrorLogService;
    @Value("${t1.kafka.topic.t1_demo_metrics}")
    private String topic;

    @Pointcut("within(ru.t1.java.demo.*)")
    public void loggingMethods() {
    }

    @AfterThrowing(pointcut = "@annotation(LogDataError)", throwing = "ex")
    public void logExceptionAnnotation(JoinPoint joinPoint, Exception ex) {

        try {
            DataSourceErrorLog dataSourceErrorLog = DataSourceErrorLog.builder()
                    .methodName(joinPoint.getSignature().getName())
                    .stackTrace(Arrays.toString(ex.getStackTrace()))
                    .message(ex.getMessage())
                    .build();

            ClientDto clientDto = ClientDto.builder().build();
            clientDto.setId(10L);
            clientDto.setFirstName("Pupkin");
            clientDto.setLastName("Vasya");
            clientDto.setMiddleName("Petrovich");

            kafkaProducerWithHeaders.sendMessageWithHeaders(
                    "t1_demo_client_registration",
                    UUID.randomUUID().toString(),
                    "ASPECT_EXCEPTION",
                    clientDto);
            // kafkaTemplate.send("t1_demo_client_registration", UUID.randomUUID().toString(), ex.getMessage());
            // kafkaTemplate.flush();
        } catch (Exception ex1) {
            log.error(ex.getMessage(), ex1);
        }
        dataSourceErrorLogService.writeErrorMessage(joinPoint, ex);
    }
}
