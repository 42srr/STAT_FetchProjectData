package com.example.STAT_FetchProjectData.client;

import com.example.STAT_FetchProjectData.client.dto.Oauth2TokenRequestDto;
import com.example.STAT_FetchProjectData.config.TokenConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FtClientImpl implements FtClient{

    private final TokenConfig tokenConfig;
    private final String PROJECT_USER_URL_PREFIX = "https://api.intra.42.fr/v2/users/";
    private final String PROJECT_USER_URL_SUFFIX = "/projects_users?filter[cursus]=21&page=";

    @Override
    public List<String> getProjects(String serverId, String oAuth2AccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuth2AccessToken);
        HttpEntity request = new HttpEntity(headers);

        log.info("access_token = {}\n", oAuth2AccessToken);
        int page = 0;
        List<String> allProjects = new ArrayList<>();
        while (true) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ArrayList> response = restTemplate.exchange(
                    PROJECT_USER_URL_PREFIX + serverId + PROJECT_USER_URL_SUFFIX + page,
                    HttpMethod.GET,
                    request,
                    ArrayList.class);

            ArrayList<HashMap<String, Object>> body = response.getBody();
            if (body.isEmpty()) break;

            allProjects.add(body.toString());
            page++;
        }
        return allProjects;
    }
}
