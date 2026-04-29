package com.matiasnuniez.ms_accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementsDTO {
    private String movementType;
    private BigDecimal value;
    private Long accountID;
}