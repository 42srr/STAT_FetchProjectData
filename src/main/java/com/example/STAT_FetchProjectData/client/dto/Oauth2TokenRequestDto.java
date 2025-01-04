package com.example.STAT_FetchProjectData.client.dto;

import lombok.Data;

@Data
public class Oauth2TokenRequestDto {
    private String grant_type;
    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
}