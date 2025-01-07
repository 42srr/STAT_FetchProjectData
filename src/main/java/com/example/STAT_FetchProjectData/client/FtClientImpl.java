package com.example.STAT_FetchProjectData.client;

import com.example.STAT_FetchProjectData.config.TokenConfig;
import com.example.STAT_FetchProjectData.repository.FtTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
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

    private final FtTokenRepository ftTokenRepository;
    private final FtTokenClient ftTokenClient;
    private final String PROJECT_USER_URL_PREFIX = "https://api.intra.42.fr/v2/users/";
    private final String PROJECT_USER_URL_SUFFIX = "/projects_users?filter[cursus]=21&page=";

    @Override
    public List<String> getProjects(String serverId) {
        int requestNum = 0;

        String oAuth2AccessToken = ftTokenRepository.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuth2AccessToken);
        HttpEntity request = new HttpEntity(headers);


        int page = 0;
        List<String> allProjects = new ArrayList<>();

        while (true) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ArrayList> response = null;
            try {
                response = restTemplate.exchange(
                        PROJECT_USER_URL_PREFIX + serverId + PROJECT_USER_URL_SUFFIX + page,
                        HttpMethod.GET,
                        request,
                        ArrayList.class);
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    ftTokenClient.refreshFtToken();
                    oAuth2AccessToken = ftTokenRepository.getAccessToken();
                } else {
                    throw new HttpClientErrorException(e.getStatusCode(), e.getResponseBodyAsString());
                }
            }

            ArrayList<HashMap<String, Object>> body = response.getBody();
            if (body.isEmpty()) break;

            allProjects.add(body.toString());
            page++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.info("thread interrupted!!");
            }

        }

        return allProjects;
    }

}
