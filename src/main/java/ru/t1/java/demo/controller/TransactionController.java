package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.demo.aop.HandlingResult;
import ru.t1.java.demo.aop.LogException;
import ru.t1.java.demo.aop.Track;
import ru.t1.java.demo.exception.TransactionException;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    @LogException
    @Track
    @GetMapping(value = "/transaction")
    @HandlingResult
    public void doSomething() throws IOException, InterruptedException {
        System.out.println("TransactionController doSomething");
        throw new TransactionException();
    }
}
