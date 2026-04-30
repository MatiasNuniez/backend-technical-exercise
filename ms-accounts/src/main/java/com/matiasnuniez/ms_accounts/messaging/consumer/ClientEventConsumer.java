package com.matiasnuniez.ms_accounts.messaging.consumer;

import com.matiasnuniez.ms_accounts.config.RabbitMQConfig;
import com.matiasnuniez.ms_accounts.dto.ClientEventDTO;
import com.matiasnuniez.ms_accounts.model.Account;
import com.matiasnuniez.ms_accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientEventConsumer {

    private final AccountRepository accountRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleClientEvent(ClientEventDTO event) {
        log.info("Event received: {} for client id {}", event.getEventType(), event.getClientId());

        switch (event.getEventType()) {
            case "CREATED" -> handleClientCreated(event);
            case "DELETED" -> handleClientDeleted(event);
            default -> log.warn("Unknown event: {}", event.getEventType());
        }
    }

    private void handleClientCreated(ClientEventDTO event) {
        Account account = new Account();
        account.setClientID(event.getClientId());
        account.setAccountNumber("ACC-" + event.getClientId() + "-" + System.currentTimeMillis());
        account.setAccountType("SAVINGS");
        account.setInitialBalance(BigDecimal.ZERO);
        account.setCurrentBalance(BigDecimal.ZERO);
        account.setState(true);
        accountRepository.save(account);
        log.info("Account created automatically for client id {}", event.getClientId());
    }

    private void handleClientDeleted(ClientEventDTO event) {
        List<Account> accounts = accountRepository.findAccountsByClientID(event.getClientId());
        accounts.forEach(account -> {
            account.setState(false);
            accountRepository.save(account);
        });
        log.info("Accounts deactivated for client id {}", event.getClientId());
    }
}
