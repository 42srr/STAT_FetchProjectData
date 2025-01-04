package com.example.STAT_FetchProjectData.config;

import com.example.STAT_FetchProjectData.client.FtClient;
import com.example.STAT_FetchProjectData.client.FtClientImpl;
import com.example.STAT_FetchProjectData.client.FtClientToken;
import com.example.STAT_FetchProjectData.client.FtClientTokenImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {
    private final TokenConfig tokenConfig;

    @Bean
    public FtClient ftClient() { return new FtClientImpl(tokenConfig); }

    @Bean
    public FtClientToken ftClientToken() { return new FtClientTokenImpl(tokenConfig); }
}
