package com.example.relaxworld.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    String apiKey = "2405650706273856";
    String secretKey = "15RrguOXoJjH5ABmQsuTnI3MKMkSplHzZQOsGwubzkv18hXGUQRmd8wwxCJS9IWFl6RQm0M2wYZFg3QN";
    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, secretKey);
    }
}
