package com.matiasnuniez.msclients.repository;

import com.matiasnuniez.msclients.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByIdentification(String identification);

    Optional<Client> findByClientID(Long clientID);
}
