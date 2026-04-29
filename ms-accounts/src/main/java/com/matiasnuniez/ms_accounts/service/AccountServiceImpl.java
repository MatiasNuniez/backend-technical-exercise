package com.matiasnuniez.ms_accounts.service;

import com.matiasnuniez.ms_accounts.dto.AccountDTO;
import com.matiasnuniez.ms_accounts.dto.AccountResponseDTO;
import com.matiasnuniez.ms_accounts.model.Account;
import com.matiasnuniez.ms_accounts.exception.ResourceNotFoundException;
import com.matiasnuniez.ms_accounts.mapper.AccountMapper;
import com.matiasnuniez.ms_accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponseDTO create(AccountDTO dto) {
        Account account = accountMapper.toEntity(dto);
        Account saved = accountRepository.save(account);
        return accountMapper.toResponseDTO(saved);
    }

    @Override
    public AccountResponseDTO update(Long id, AccountDTO dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

        account.setAccountNumber(dto.getAccountNumber());
        account.setAccountType(dto.getAccountType());
        account.setState(dto.getState());

        Account updated = accountRepository.save(account);
        return accountMapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        accountRepository.delete(account);
    }

    @Override
    public AccountResponseDTO findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        return accountMapper.toResponseDTO(account);
    }

    @Override
    public List<AccountResponseDTO> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
