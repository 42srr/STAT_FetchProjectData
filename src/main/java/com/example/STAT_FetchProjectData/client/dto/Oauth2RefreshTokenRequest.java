package com.example.STAT_FetchProjectData.client.dto;

import lombok.Data;

@Data
public class Oauth2RefreshTokenRequest {
    private String grant_type;
    private String refresh_token;
}
