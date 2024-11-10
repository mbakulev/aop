package ru.t1.java.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.dto.TransactionDto;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.TransactionService;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public Transaction createTransaction(TransactionDto transactionDto) {

        Transaction transaction = Transaction.builder()
                .accountId(transactionDto.getAccountId())
                .amount(transactionDto.getAmount())
                .dateTime(LocalDateTime.now())
                .build();

        System.out.println("Transaction created: " + transaction.getAccountId() + " " + transaction.getAmount() + " " + transaction.getDateTime());
        return transactionRepository.save(transaction);
    }

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .accountId(transactionDto.getAccountId())
                .amount(transactionDto.getAmount())
                .dateTime(LocalDateTime.now())
                .build();

        System.out.println("Transaction saved: " + transaction.getAccountId() + " " + transaction.getAmount() + " " + transaction.getDateTime());
       transactionRepository.save(transaction);
    }
}
