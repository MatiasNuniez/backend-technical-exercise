package com.matiasnuniez.ms_accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private Boolean state;
    private Long clientID;
}
