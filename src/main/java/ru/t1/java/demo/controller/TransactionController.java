package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.demo.aop.HandlingResult;
import ru.t1.java.demo.aop.LogDataError;
import ru.t1.java.demo.aop.LogException;
import ru.t1.java.demo.aop.Track;
import ru.t1.java.demo.dto.TransactionDto;
import ru.t1.java.demo.exception.TransactionException;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.service.TransactionService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @LogException
    @Track
    @GetMapping(value = "/transaction")
    @HandlingResult
    public void doSomething() throws IOException, InterruptedException {
        System.out.println("TransactionController doSomething");
        throw new TransactionException();
    }

    @LogDataError
    @GetMapping(value = "/transaction/{id}")
    public Transaction getTransaction(@PathVariable Long id) throws IOException, InterruptedException {
        System.out.println("Transaction.getTransaction");
        return transactionService.getTransaction(id);
    }

    @LogDataError
    @PostMapping(value = "/transaction")
    public Transaction createTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.createTransaction(transactionDto);
    }
}
