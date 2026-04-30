package com.matiasnuniez.msclients.messaging.producer;

import com.matiasnuniez.msclients.config.RabbitMQConfig;
import com.matiasnuniez.msclients.dto.ClientEventDTO;
import com.matiasnuniez.msclients.model.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientEventProducer {

    private final AmqpTemplate amqpTemplate;

    public void publishClientCreated(Client client) {
        ClientEventDTO event = new ClientEventDTO(
                client.getClientID(),
                "CREATED",
                client.getName()
        );
        amqpTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
        log.info("Event published: with id {}", client.getClientID());
    }

    public void publishClientDeleted(Long clientId) {
        ClientEventDTO event = new ClientEventDTO(
                clientId,
                "DELETED",
                null
        );
        amqpTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
        log.info("Event published: client deleted with id {}", clientId);
    }
}
