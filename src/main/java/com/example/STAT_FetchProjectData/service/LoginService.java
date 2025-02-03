package com.example.STAT_FetchProjectData.service;

import com.example.STAT_FetchProjectData.client.tokenClient.FtTokenClient;
import com.example.STAT_FetchProjectData.repository.FtTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final FtTokenClient ftTokenClient;
    private final FtTokenRepository ftTokenRepository;

    public String registerAccessToken(String code) {
        ftTokenRepository.saveAccessToken(ftTokenClient.getFtToken(code));
        return "OK";
    }
}
