package com.matiasnuniez.ms_accounts.mapper;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.matiasnuniez.ms_accounts.dto.MovementsDTO;
import com.matiasnuniez.ms_accounts.dto.MovementsResponseDTO;
import com.matiasnuniez.ms_accounts.model.Movements;
import com.matiasnuniez.ms_accounts.model.Account;

@Component
public class MovementsMapper {

    public MovementsResponseDTO toResponseDTO (Movements movements){
        MovementsResponseDTO dto = new MovementsResponseDTO();
        dto.setMovementID(movements.getMovementID());
        dto.setAccountId(movements.getAccount().getAccountID());
        dto.setDate(movements.getDate());
        dto.setBalance(movements.getBalance());
        dto.setMovementType(movements.getMovementType());
        dto.setValue(movements.getValue());
        return dto;
    }

    public Movements toEntity(MovementsDTO dto, Account account){
        Movements movements = new Movements();
        movements.setDate(LocalDateTime.now());
        movements.setAccount(account);
        movements.setMovementType(dto.getMovementType());
        movements.setValue(dto.getValue());
        return movements;
    }

}
