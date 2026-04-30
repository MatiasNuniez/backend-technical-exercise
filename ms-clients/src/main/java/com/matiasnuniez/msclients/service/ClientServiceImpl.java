package com.matiasnuniez.msclients.service;

import lombok.RequiredArgsConstructor;
import com.matiasnuniez.msclients.dto.ClientDTO;
import java.util.List;
import java.util.stream.Collectors;
import com.matiasnuniez.msclients.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.matiasnuniez.msclients.mapper.ClientMapper;
import com.matiasnuniez.msclients.dto.ClientResponseDTO;
import com.matiasnuniez.msclients.repository.ClientRepository;
import com.matiasnuniez.msclients.model.Client;
import com.matiasnuniez.msclients.messaging.producer.ClientEventProducer;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientEventProducer clientEventProducer;

    @Override
    public ClientResponseDTO create(ClientDTO dto) {
        Client client = clientMapper.toEntity(dto);
        Client savedClient = clientRepository.save(client);
        clientEventProducer.publishClientCreated(savedClient);
        return clientMapper.toResponseDTO(savedClient);
    }

    @Override
    public ClientResponseDTO update(Long id, ClientDTO dto) {
        Client client = clientRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        client.setName(dto.getName());
        client.setAge(dto.getAge());
        client.setAddress(dto.getAddress());
        client.setGender(dto.getGender());
        client.setIdentification(dto.getIdentification());
        client.setPhone(dto.getPhone());
        client.setAddress(dto.getAddress());
        client.setState(dto.getState());
        client.setPassword(dto.getPassword());

        Client updatedClient = clientRepository.save(client);
        return clientMapper.toResponseDTO(updatedClient);
    }

    @Override
    public void delete(Long id) {
        Client client = clientRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        clientRepository.delete(client);
        clientEventProducer.publishClientDeleted(id);
    }

    @Override
    public ClientResponseDTO findById(Long id) {
        Client client = clientRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return clientMapper.toResponseDTO(client);
    }

    @Override
    public List<ClientResponseDTO> findAll() {
        return clientRepository.findAll()
        .stream()
        .map(clientMapper::toResponseDTO)
        .collect(Collectors.toList());
    }    
}