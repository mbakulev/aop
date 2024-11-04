package ru.t1.java.demo.service;

import ru.t1.java.demo.dto.AccountDto;
import ru.t1.java.demo.model.Account;

import java.util.Optional;

public interface AccountService {
    Account getAccount(Long id);
    Account createAccount(AccountDto dto);
    Account updateAccount(AccountDto account);
    void deleteAccount(Long id);
}
