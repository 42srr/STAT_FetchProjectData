package com.example.STAT_FetchProjectData.repository;

import com.example.STAT_FetchProjectData.client.dto.Oauth2TokenResponseDto;

public interface FtTokenRepository {

    String getAccessToken();
    void saveAccessToken(Oauth2TokenResponseDto token);
    void refreshAccessToken();
}
