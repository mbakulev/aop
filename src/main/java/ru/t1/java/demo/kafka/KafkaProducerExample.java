//package ru.t1.java.demo.kafka;
//
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.header.Header;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class KafkaProducerExample {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    public KafkaProducerExample(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendMessageWithHeaders(String topic, String key, String value) {
//        ProducerRecord<String, String> record = new ProducerRecord<>(
//                topic,
//                key,
//                value
//        );
//
//        // Add headers
//        Map<String, String> headers = new HashMap<>();
//        headers.put("header-key-1", "header-value-1");
//        headers.put("header-key-2", "header-value-2");
//
//        record.headers().add((Header) headers);
//
//        kafkaTemplate.send(record);
//    }
//
//    public static void main(String[] args) {
//        // Assuming KafkaConfig is already configured and available
//        KafkaProducerExample producer = new KafkaProducerExample(new KafkaTemplate<>());
//
//        producer.sendMessageWithHeaders("my-topic", "key", "value");
//    }
//}
