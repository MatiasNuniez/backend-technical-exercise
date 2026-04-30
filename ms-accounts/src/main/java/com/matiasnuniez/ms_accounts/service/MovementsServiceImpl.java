package com.matiasnuniez.ms_accounts.service;

import com.matiasnuniez.ms_accounts.client.MsClientsRestClient;
import com.matiasnuniez.ms_accounts.dto.AccountDetailsDTO;
import com.matiasnuniez.ms_accounts.dto.ClientEventDTO;
import com.matiasnuniez.ms_accounts.dto.StateAccountDTO;
import com.matiasnuniez.ms_accounts.dto.MovementsDTO;
import com.matiasnuniez.ms_accounts.dto.MovementsResponseDTO;
import com.matiasnuniez.ms_accounts.model.Account;
import com.matiasnuniez.ms_accounts.model.Movements;
import com.matiasnuniez.ms_accounts.exception.InsufficientBalanceException;
import com.matiasnuniez.ms_accounts.exception.ResourceNotFoundException;
import com.matiasnuniez.ms_accounts.mapper.MovementsMapper;
import com.matiasnuniez.ms_accounts.repository.AccountRepository;
import com.matiasnuniez.ms_accounts.repository.MovementsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MovementsServiceImpl implements MovementsService {

    private final MovementsRepository movementsRepository;
    private final AccountRepository accountRepository;
    private final MovementsMapper movementsMapper;
    private final MsClientsRestClient msClientsRestClient;
    @Override
    public MovementsResponseDTO create(MovementsDTO dto) {
        Account account = accountRepository.findById(dto.getAccountID())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + dto.getAccountID()));

        
        BigDecimal newBalance = account.getCurrentBalance().add(dto.getValue());
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for this movement. Current balance: " + account.getCurrentBalance());
        }

        account.setCurrentBalance(newBalance);
        accountRepository.save(account);

        Movements movements = movementsMapper.toEntity(dto, account);
        movements.setBalance(newBalance);
        Movements saved = movementsRepository.save(movements);

        return movementsMapper.toResponseDTO(saved);
    }

    @Override
    public MovementsResponseDTO update(Long id, MovementsDTO dto) {
        Movements movements = movementsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movement not found with id: " + id));

        movements.setMovementType(dto.getMovementType());
        movements.setValue(dto.getValue());

        Movements updated = movementsRepository.save(movements);
        return movementsMapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Movements movements = movementsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movement not found with id: " + id));
        movementsRepository.delete(movements);
    }

    @Override
    public MovementsResponseDTO findById(Long id) {
        Movements movements = movementsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movement not found with id: " + id));
        return movementsMapper.toResponseDTO(movements);
    }

    @Override
    public List<MovementsResponseDTO> findAll() {
        return movementsRepository.findAll()
                .stream()
                .map(movementsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StateAccountDTO> getAccountStatement(Long clientId, LocalDateTime startDate, LocalDateTime endDate) {
        ClientEventDTO client = msClientsRestClient.getClient(clientId);
        List<Account> accounts = accountRepository.findAccountsByClientID(clientId);

        return accounts.stream().map(account -> {
            List<Movements> movements = movementsRepository
                    .findByAccountAndDateBetween(account, startDate, endDate);

            AccountDetailsDTO accountDetail = new AccountDetailsDTO();
            accountDetail.setAccountNumber(account.getAccountNumber());
            accountDetail.setAccountType(account.getAccountType());
            accountDetail.setInitialBalance(account.getInitialBalance());
            accountDetail.setCurrentBalance(account.getCurrentBalance());
            accountDetail.setState(account.getState());
            accountDetail.setMovements(movements.stream()
                    .map(movementsMapper::toResponseDTO)
                    .collect(Collectors.toList()));

            StateAccountDTO stateAccount = new StateAccountDTO();
            stateAccount.setClientID(clientId);
            stateAccount.setName(client.getName());
            stateAccount.setAccounts(List.of(accountDetail));
            return stateAccount;
        }).collect(Collectors.toList());
    }
}
