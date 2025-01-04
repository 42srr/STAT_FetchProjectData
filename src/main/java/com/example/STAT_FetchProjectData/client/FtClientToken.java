package com.example.STAT_FetchProjectData.client;

import com.example.STAT_FetchProjectData.client.dto.Oauth2TokenResponseDto;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface FtClientToken {
    ConcurrentLinkedQueue<Oauth2TokenResponseDto> dataBase = new ConcurrentLinkedQueue<>();
    String getAccessToken();
    void saveAccessToken(String code);
    void refreshAccessToken();
}
