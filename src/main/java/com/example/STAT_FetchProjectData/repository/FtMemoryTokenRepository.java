package com.example.STAT_FetchProjectData.repository;

import com.example.STAT_FetchProjectData.client.dto.Oauth2TokenResponseDto;
import com.example.STAT_FetchProjectData.client.exception.TokenNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Component
@RequiredArgsConstructor
public class FtMemoryTokenRepository implements FtTokenRepository {

    ConcurrentLinkedQueue<Oauth2TokenResponseDto> dataBase = new ConcurrentLinkedQueue<>();

    @Override
    public String getAccessToken() {
        if(dataBase.peek() == null)
            throw new TokenNotFoundException("Fail to load Access Token");
        return dataBase.peek().getAccess_token();
    }

    @Override
    public String getRefreshToken() {
        if(dataBase.peek() == null)
            throw new TokenNotFoundException("Fail to load Refresh Token");
        return dataBase.peek().getRefresh_token();
    }

    @Override
    public void saveAccessToken(Oauth2TokenResponseDto token) {
        dataBase.clear();
        dataBase.add(token);
    }
}
