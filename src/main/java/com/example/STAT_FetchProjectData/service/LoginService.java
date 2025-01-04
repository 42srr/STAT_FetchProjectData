package com.example.STAT_FetchProjectData.service;

import com.example.STAT_FetchProjectData.client.FtClientToken;
import com.example.STAT_FetchProjectData.client.FtClientTokenImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final FtClientTokenImpl ftClientToken;

    public String registerAccessToken(String code) {
        ftClientToken.saveAccessToken(code);
        return "OK";
    }
}
