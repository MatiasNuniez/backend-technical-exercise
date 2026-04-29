package com.matiasnuniez.ms_accounts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementID;

    private LocalDateTime date;
    private String movementType;
    private BigDecimal value;
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "accountID", nullable = false)
    private Account account;
}
