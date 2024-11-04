package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.demo.aop.HandlingResult;
import ru.t1.java.demo.aop.LogDataError;
import ru.t1.java.demo.aop.LogException;
import ru.t1.java.demo.aop.Track;
import ru.t1.java.demo.dto.AccountDto;
import ru.t1.java.demo.exception.AccountException;
import ru.t1.java.demo.exception.ClientException;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.service.AccountService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @LogException
    @Track
    @GetMapping(value = "/account")
    @HandlingResult
    public void doSomething() throws IOException, InterruptedException {
        System.out.println("AccountController.doSomething");
        throw new AccountException();
    }

    @LogDataError
    @GetMapping(value = "/account/{id}")
    public Account getAccount(@PathVariable("id") Long id) {
        System.out.println("AccountController.getAccount");
        return accountService.getAccount(id);
    }

    @LogDataError
    @PostMapping(value = "/account")
    public Account createAccount(@RequestBody AccountDto accountDto) {
        System.out.println("AccountController.createAccount");
        return accountService.createAccount(accountDto);
    }

    @LogDataError
    @DeleteMapping(value = "/account/{id}")
    public void deleteAccount(@PathVariable("id") Long id) {
        System.out.println("AccountController.deleteAccount");
        accountService.deleteAccount(id);
    }

    @LogDataError
    @PutMapping(value = "/account")
    public Account updateAccount(@RequestBody AccountDto accountDto) {
        System.out.println("AccountController.updateAccount");
        return accountService.updateAccount(accountDto);
    }
}
