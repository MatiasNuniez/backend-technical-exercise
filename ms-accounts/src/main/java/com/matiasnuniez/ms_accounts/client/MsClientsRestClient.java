package com.matiasnuniez.ms_accounts.client;

import com.matiasnuniez.ms_accounts.dto.ClientEventDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
@Slf4j
public class MsClientsRestClient {

    private final RestTemplate restTemplate;

    @Value("${ms.clients.url}")
    private String msClientsUrl;

    @CircuitBreaker(name = "ms-clients")
    @Retry(name = "ms-clients", fallbackMethod = "getClientFallback")
    public ClientEventDTO getClient(Long clientID) {
        log.info("consulted client with id {}", clientID);
        return restTemplate.getForObject(
                msClientsUrl + "/clients/" + clientID,
                ClientEventDTO.class
        );
    }

    public ClientEventDTO getClientFallback(Long clientID, Exception ex) {
        log.warn("Fallback activated for client id {}. Error: {}", clientID, ex.getMessage());
        ClientEventDTO fallback = new ClientEventDTO();
        fallback.setClientId(clientID);
        fallback.setEventType("UNKNOWN");
        fallback.setName("Unknown Client");
        return fallback;
    }
}