package ru.t1.java.demo.service;

import ru.t1.java.demo.dto.TransactionDto;
import ru.t1.java.demo.model.Transaction;

public interface TransactionService {
    Transaction getTransaction(Long id);
    void createTransaction(TransactionDto dto);
    void saveTransaction(TransactionDto dto);
    Transaction saveTransactionWithStatus(TransactionDto dto, String status);
    void updateTransactionStatus(Long id, String status);
}
