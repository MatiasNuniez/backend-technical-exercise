package com.matiasnuniez.ms_accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateAccountDTO {
    private Long clientID;
    private String name;
    private List<AccountDetailsDTO> accounts;
}
