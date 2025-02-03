package com.example.STAT_FetchProjectData.client.projectsClient;

import com.example.STAT_FetchProjectData.client.tokenClient.FtTokenClient;
import com.example.STAT_FetchProjectData.repository.FtMemoryTokenRepository;
import com.example.STAT_FetchProjectData.service.dto.ProjectDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectsClient {
    private final FtTokenClient ftTokenClient;
    private final FtMemoryTokenRepository ftTokenRepository;
    private final String PROJECTS_URL= "https://api.intra.42.fr/v2/cursus/21/projects?page=";

    public List<ProjectDto> fetchProjects(int page) {
        String oAuth2AccessToken = ftTokenRepository.getAccessToken();
        HttpHeaders headers = getHttpHeaders(oAuth2AccessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = PROJECTS_URL + page;

        try {
            ResponseEntity<List<ProjectDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<ProjectDto>>() {}
            );
            Thread.sleep(500);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            return handleHTTPError(page, e);
        } catch (InterruptedException e) {
            log.info("thread interrupted!!");
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private HttpHeaders getHttpHeaders(String oAuth2AccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + oAuth2AccessToken);
        return headers;
    }

    private List<ProjectDto> handleHTTPError(int page, HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            ftTokenClient.refreshFtToken();
            return fetchProjects(page);
        } else {
            throw new HttpClientErrorException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }
}
