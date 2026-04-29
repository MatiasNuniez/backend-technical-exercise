package com.matiasnuniez.ms_accounts.service;

import com.matiasnuniez.ms_accounts.dto.AccountDTO;
import com.matiasnuniez.ms_accounts.dto.AccountResponseDTO;
import java.util.List;

public interface AccountService {
    AccountResponseDTO create(AccountDTO dto);
    AccountResponseDTO update(Long id, AccountDTO dto);
    void delete(Long id);
    AccountResponseDTO findById(Long id);
    List<AccountResponseDTO> findAll();
}
