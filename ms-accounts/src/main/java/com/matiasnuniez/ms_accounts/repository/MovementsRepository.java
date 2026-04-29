package com.matiasnuniez.ms_accounts.repository;

import com.matiasnuniez.ms_accounts.model.Account;
import com.matiasnuniez.ms_accounts.model.Movements;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovementsRepository extends JpaRepository<Movements, Long> {

    List<Movements> findByAccount(Account account);

    List<Movements> findByAccountAndDateBetween(Account account, LocalDateTime startDate, LocalDateTime endDate);

}
