package com.example.STAT_FetchProjectData.client;

import com.example.STAT_FetchProjectData.client.dto.Oauth2TokenResponseDto;

public interface FtTokenClient {
    Oauth2TokenResponseDto getFtToken(String code);
    void refreshFtToken();
}
