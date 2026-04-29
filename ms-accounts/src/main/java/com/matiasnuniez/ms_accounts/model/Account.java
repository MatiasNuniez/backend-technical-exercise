package com.matiasnuniez.ms_accounts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountID;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    private String accountType;
    private BigDecimal initialBalance;
    private BigDecimal currentBalance;
    private Boolean state;
    private Long clientID;
}
