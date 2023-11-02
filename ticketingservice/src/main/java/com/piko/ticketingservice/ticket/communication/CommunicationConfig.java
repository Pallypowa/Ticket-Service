package com.piko.ticketingservice.ticket.communication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class CommunicationConfig {
    @Value("${ticketingservice.partnerURI}")
    private String BASE_URI;
    @Value("${ticketingservice.userName}")
    private String USER_NAME;
    @Value("${ticketingservice.password}")
    private String PASSWORD;

    @Bean
    RestTemplate rest(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.rootUri(BASE_URI)
                .setConnectTimeout(Duration.ofMillis(5000))
                .errorHandler(new PartnerClientErrorHandler())
                .basicAuthentication(USER_NAME, PASSWORD)
                .build();
    }

}
