package ru.t1.java.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.dto.AccountDto;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.service.AccountService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account createAccount(AccountDto accountDto) {
        Account account = Account.builder()
                .clientId(accountDto.getClientId())
                .type(String.valueOf(accountDto.getAccountType()))
                .balance(accountDto.getBalance())
                .build();;

        System.out.println("new account: " + account);
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(AccountDto accountDto) {
        Account account = getAccount(accountDto.getId());
        account.setClientId(accountDto.getClientId());
        account.setBalance(accountDto.getBalance());

        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
