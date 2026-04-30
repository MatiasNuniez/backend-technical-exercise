package com.matiasnuniez.ms_accounts;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class KarateRunner {

    @Karate.Test
    Karate testAccounts() {
        return Karate.run("classpath:karate/accounts/accounts.feature")
                .relativeTo(getClass());
    }
}