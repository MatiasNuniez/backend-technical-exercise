package com.matiasnuniez.ms_accounts.controller;

import com.matiasnuniez.ms_accounts.dto.MovementsDTO;
import com.matiasnuniez.ms_accounts.dto.MovementsResponseDTO;
import com.matiasnuniez.ms_accounts.service.MovementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movements")
@RequiredArgsConstructor
public class MovementsController {

    private final MovementsService movementsService;

    @GetMapping
    public ResponseEntity<List<MovementsResponseDTO>> findAll() {
        return ResponseEntity.ok(movementsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementsResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movementsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MovementsResponseDTO> create(@RequestBody MovementsDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movementsService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovementsResponseDTO> update(@PathVariable Long id, @RequestBody MovementsDTO dto) {
        return ResponseEntity.ok(movementsService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movementsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
