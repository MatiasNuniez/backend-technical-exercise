package com.matiasnuniez.msclients.mapper;
import com.matiasnuniez.msclients.dto.ClientResponseDTO;
import com.matiasnuniez.msclients.model.Client;
@Component
public class ClientMapper {

    public ClientResponseDTO toResponseDTO(Client client){
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setClientID(client.getClientID());
        dto.setName(client.getName());
        dto.setAge(client.getAge());
        dto.setAddress(client.getAddress());
        dto.setGender(client.getGender());
        dto.setIdentification(client.getIdentification());
        dto.setPhone(client.getPhone());
        dto.setState(client.getState());
        return dto;
    }

    public Client toEntity(ClientResponseDTO dto){
        Client client = new Client();
        client.setClientID(dto.getClientID());
        client.setName(dto.getName());
        client.setAge(dto.getAge());
        client.setAddress(dto.getAddress());
        client.setGender(dto.getGender());
        client.setIdentification(dto.getIdentification());
        client.setPhone(dto.getPhone());
        client.setState(dto.getState());
        return client;
    }

}
