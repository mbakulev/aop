package ru.t1.java.demo.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.dto.ClientDto;

@Service
public class KafkaProducerWithHeaders {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerWithHeaders(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageWithHeaders(String topic, String key, String customHeader, Object payload) {
        System.out.println("MESSAGE_KAFKA");
//        Message<Object> mes = MessageBuilder
//                .withPayload(payload)
//                .setHeader(KafkaHeaders.TOPIC, topic)
//                .setHeader(KafkaHeaders.KEY, key)
//                .setHeader(customHeader, payload.toString())
//                .build();
        Message<Object> message = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.KEY, key)
                .setHeader(customHeader, payload.toString())
                .build();

        kafkaTemplate.send(message);
    }
}