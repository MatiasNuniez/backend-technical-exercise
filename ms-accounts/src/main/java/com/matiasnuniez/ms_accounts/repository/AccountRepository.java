package com.matiasnuniez.ms_accounts.repository;

import com.matiasnuniez.ms_accounts.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByAccountNumber(String accountNumber);

    List<Account> findAccountsByClientID(Long clientID);
}
