package com.matiasnuniez.ms_accounts.service;

import com.matiasnuniez.ms_accounts.dto.StateAccountDTO;
import com.matiasnuniez.ms_accounts.dto.MovementsDTO;
import com.matiasnuniez.ms_accounts.dto.MovementsResponseDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface MovementsService {
    MovementsResponseDTO create(MovementsDTO dto);
    MovementsResponseDTO update(Long id, MovementsDTO dto);
    void delete(Long id);
    MovementsResponseDTO findById(Long id);
    List<MovementsResponseDTO> findAll();
    List<StateAccountDTO> getAccountStatement(Long clientId, LocalDateTime startDate, LocalDateTime endDate);
}