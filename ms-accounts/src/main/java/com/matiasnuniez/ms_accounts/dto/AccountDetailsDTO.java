package com.matiasnuniez.ms_accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsDTO {
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private BigDecimal currentBalance;
    private Boolean state;
    private List<MovementsResponseDTO> movements;

}
