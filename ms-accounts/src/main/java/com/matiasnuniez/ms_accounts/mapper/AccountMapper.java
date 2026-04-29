package com.matiasnuniez.ms_accounts.mapper;
import com.matiasnuniez.ms_accounts.model.Account;

import org.springframework.stereotype.Component;

import com.matiasnuniez.ms_accounts.dto.AccountDTO;
import com.matiasnuniez.ms_accounts.dto.AccountResponseDTO;

@Component
public class AccountMapper {

    public AccountResponseDTO toResponseDTO(Account account){
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setAccountID(account.getAccountID());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountType(account.getAccountType());
        dto.setInitialBalance(account.getInitialBalance());
        dto.setCurrentBalance(account.getCurrentBalance());
        dto.setClientID(account.getClientID());
        dto.setState(account.getState());
        return dto;
    } 

    public Account toEntity(AccountDTO dto){
        Account account = new Account();
        account.setAccountNumber(dto.getAccountNumber());
        account.setAccountType(dto.getAccountType());
        account.setInitialBalance(dto.getInitialBalance());
        account.setClientID(dto.getClientID());
        account.setState(dto.getState());
        account.setCurrentBalance(dto.getInitialBalance());
        return account;
    }

    }