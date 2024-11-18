package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.TransactionAccept;
import ru.t1.java.demo.dto.TransactionAcceptResult;
import ru.t1.java.demo.dto.TransactionDto;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;

import java.util.UUID;

import static java.lang.System.out;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTransactionAcceptConsumer {
    private final KafkaTemplate<String, TransactionAcceptResult> kafkaTemplate;
    @KafkaListener(id = "${t1.kafka.consumer.t1_demo_transaction_accept}",
            topics = "${t1.kafka.topic.t1_demo_transaction_accept}",
            containerFactory = "transactionAcceptKafkaListenerContainerFactory")
    public void listener(@Payload TransactionAccept transactionAccept,
                         Acknowledgment ack) {
        log.debug("KafkaTransactionAcceptConsumer: Обработка новых сообщений");

        try {
            out.println("TransactionAccept: " + transactionAccept);

            TransactionAcceptResult transactionAcceptResult = new TransactionAcceptResult();
            kafkaTemplate.send("t1_demo_transaction_result", UUID.randomUUID().toString(), transactionAcceptResult);
            kafkaTemplate.flush();
        } finally {
            ack.acknowledge();
        }

        log.debug("KafkaTransactionAcceptConsumer: записи обработаны");
    }
}
