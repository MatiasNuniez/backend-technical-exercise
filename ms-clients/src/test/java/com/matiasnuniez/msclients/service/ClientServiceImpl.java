package com.matiasnuniez.msclients.service;

import com.matiasnuniez.msclients.dto.ClientDTO;
import com.matiasnuniez.msclients.dto.ClientResponseDTO;
import com.matiasnuniez.msclients.model.Client;
import com.matiasnuniez.msclients.exception.ResourceNotFoundException;
import com.matiasnuniez.msclients.mapper.ClientMapper;
import com.matiasnuniez.msclients.messaging.producer.ClientEventProducer;
import com.matiasnuniez.msclients.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ClientEventProducer clientEventProducer;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;
    private ClientDTO clientDTO;
    private ClientResponseDTO clientResponseDTO;

    @BeforeEach
    void setUp() {
        clientDTO = new ClientDTO();
        clientDTO.setName("Juan Perez");
        clientDTO.setIdentification("1234567890");
        clientDTO.setPassword("password123");
        clientDTO.setState(true);

        client = new Client();
        client.setClientID(1L);
        client.setName("Juan Perez");
        client.setIdentification("1234567890");
        client.setState(true);

        clientResponseDTO = new ClientResponseDTO();
        clientResponseDTO.setClientID(1L);
        clientResponseDTO.setName("Juan Perez");
        clientResponseDTO.setState(true);
    }

    @Test
    void create_ShouldReturnClientResponseDTO_WhenClientIsCreated() {

        when(clientMapper.toEntity(clientDTO)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.toResponseDTO(client)).thenReturn(clientResponseDTO);

        ClientResponseDTO result = clientService.create(clientDTO);

        assertNotNull(result);
        assertEquals(1L, result.getClientID());
        assertEquals("Juan Perez", result.getName());
        verify(clientRepository, times(1)).save(client);
        verify(clientEventProducer, times(1)).publishClientCreated(client);
    }

    @Test
    void findById_ShouldThrowException_WhenClientNotFound() {
        
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.findById(99L));
    }

    @Test
    void delete_ShouldDeleteClient_WhenClientExists() {
        
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        
        clientService.delete(1L);

        verify(clientRepository, times(1)).delete(client);
        verify(clientEventProducer, times(1)).publishClientDeleted(1L);
    }
}