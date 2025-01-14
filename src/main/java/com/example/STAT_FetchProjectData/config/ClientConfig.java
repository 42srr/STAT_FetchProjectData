package com.example.STAT_FetchProjectData.config;

import com.example.STAT_FetchProjectData.client.FtClient;
import com.example.STAT_FetchProjectData.client.FtClientImpl;
import com.example.STAT_FetchProjectData.client.FtTokenClient;
import com.example.STAT_FetchProjectData.client.FtTokenClientImpl;
import com.example.STAT_FetchProjectData.repository.FtTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {
    private final TokenConfig tokenConfig;
    private final FtTokenRepository ftTokenRepository;
    private final FtTokenClient ftTokenClient;


    @Bean
    public FtClient ftClient() { return new FtClientImpl(ftTokenRepository, ftTokenClient); }

    @Bean
    public FtTokenClient ftTokenClient() { return new FtTokenClientImpl(tokenConfig, ftTokenRepository); }
}
