package com.matiasnuniez.msclients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO extends PersonDTO {
    private String password;
    private Boolean state;
}
