package com.example.STAT_FetchProjectData.client;

import com.example.STAT_FetchProjectData.client.dto.Oauth2TokenRequestDto;
import com.example.STAT_FetchProjectData.client.dto.Oauth2TokenResponseDto;
import com.example.STAT_FetchProjectData.config.TokenConfig;
import com.example.STAT_FetchProjectData.client.exception.TokenNotFoundException;
import com.example.STAT_FetchProjectData.client.exception.TokenRequestFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class FtClientTokenImpl implements FtClientToken {

    private final TokenConfig tokenConfig;

    @Override
    public String getAccessToken() {
        if(dataBase.peek() == null)
            throw new TokenNotFoundException("Fail to load Access Token");
        return dataBase.peek().getAccess_token();
    }

    @Override
    public void saveAccessToken(String code) {
        Oauth2TokenRequestDto requestDto = new Oauth2TokenRequestDto();
        requestDto.setCode(code);
        requestDto.setGrant_type(tokenConfig.getGrantType());
        requestDto.setClient_id(tokenConfig.getClientId());;
        requestDto.setClient_secret(tokenConfig.getClientSecret());
        requestDto.setRedirect_uri(tokenConfig.getRedirectURI());

        log.info("=== Token Request Dto ===\n{}", requestDto.toString());

        RestTemplate restTemplate = new RestTemplate();

        Oauth2TokenResponseDto responseDto;
        try {
            responseDto = restTemplate.postForObject(
                    tokenConfig.getTokenURI(),
                    requestDto,
                    Oauth2TokenResponseDto.class
            );
        } catch (HttpClientErrorException e) {
            throw new TokenRequestFailedException("Fail to get Access Token");
        }

        log.info("=== token ==={}\n", responseDto.toString());
        dataBase.clear();
        dataBase.add(responseDto);
    }

    @Override
    public void refreshAccessToken() {

    }
}
