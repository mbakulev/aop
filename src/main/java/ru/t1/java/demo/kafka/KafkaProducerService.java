package ru.t1.java.demo.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageWithHeaders(String topic, String key, String value, String headerKey, String headerValue) {
        // Create a list to hold the headers
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader(headerKey, headerValue.getBytes())); // Add your custom header

        // Create a ProducerRecord with the headers
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, key, value, headers);

        // Send the message with the KafkaTemplate
        ListenableFuture<SendResult<String, String>> future = (ListenableFuture<SendResult<String, String>>) kafkaTemplate.send(record);

        // Add a callback for handling success or failure
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + value + "] with key=[" + key + "] and header=[" + headerKey + ":" + headerValue + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.err.println("Unable to send message=[" + value + "] due to : " + ex.getMessage());
            }
        });
    }
}
