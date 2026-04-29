package com.matiasnuniez.ms_accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementsResponseDTO {
    private Long movementID;
    private LocalDateTime date;
    private String movementType;
    private BigDecimal value;
    private BigDecimal balance;
    private Long accountId;
}
