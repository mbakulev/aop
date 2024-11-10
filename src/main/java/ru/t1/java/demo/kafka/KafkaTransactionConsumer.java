package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.TransactionDto;
import ru.t1.java.demo.service.TransactionService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTransactionConsumer {

    private final TransactionService transactionService;

    @KafkaListener(id = "${t1.kafka.consumer.transaction-group-id}",
            topics = "${t1.kafka.topic.client_transactions}",
            containerFactory = "transactionKafkaListenerContainerFactory")
    public void listener(@Payload List<TransactionDto> transactionDtos,
                         Acknowledgment ack) {
        log.debug("KafkaTransactionConsumer: Обработка новых сообщений");

        try {
            transactionDtos.forEach(transactionService::saveTransaction);
        } finally {
            ack.acknowledge();
        }

        log.debug("KafkaTransactionConsumer: записи обработаны");
    }
}
