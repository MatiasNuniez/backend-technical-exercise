package com.matiasnuniez.msclients.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEventDTO {
    private Long clientId;
    private String eventType;
    private String name;
}