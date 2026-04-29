package com.matiasnuniez.ms_accounts.mapper;
import java.time.LocalDateTime;
import com.matiasnuniez.ms_accounts.dto.MovementsResponseDTO;
import com.matiasnuniez.ms_accounts.model.Movements;
import com.matiasnuniez.ms_accounts.model.Account;

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

    public Movements toEntity(MovementsResponseDTO dto, Account account){
        Movements movements = new Movements();
        movements.setDate(LocalDateTime.now());
        movements.setAccount(account);
        movements.setMovementType(dto.getMovementType());
        movements.setValue(dto.getValue());
        return movements;
    }

}
