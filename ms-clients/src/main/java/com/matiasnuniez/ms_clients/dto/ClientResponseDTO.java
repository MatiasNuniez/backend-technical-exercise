package com.matiasnuniez.msclients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO extends PersonDTO {
    private Long clientID;
    private Boolean state;
}
