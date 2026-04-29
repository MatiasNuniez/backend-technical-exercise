package com.matiasnuniez.msclients.service;

import com.matiasnuniez.msclients.dto.ClientResponseDTO;
import com.matiasnuniez.msclients.dto.ClientDTO;
import java.util.List;

public interface ClientService {
    
    ClientResponseDTO create(ClientDTO dto);
    ClientResponseDTO update(Long id, ClientDTO dto);
    void delete(Long id);
    ClientResponseDTO findById(Long id);
    List<ClientResponseDTO> findAll();

}
