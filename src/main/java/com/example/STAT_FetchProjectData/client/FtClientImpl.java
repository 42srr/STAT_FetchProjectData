package com.example.STAT_FetchProjectData.client;

import com.example.STAT_FetchProjectData.client.dto.ParsingResponseDto;
import com.example.STAT_FetchProjectData.client.dto.ProjectDto;
import com.example.STAT_FetchProjectData.client.dto.TeamsDto;
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
public class FtClientImpl implements FtClient {

    private final FtTokenRepository ftTokenRepository;
    private final FtTokenClient ftTokenClient;
    private final String PROJECT_USER_URL_PREFIX = "https://api.intra.42.fr/v2/users/";
    private final String PROJECT_USER_URL_SUFFIX = "/projects_users?filter[cursus]=21&page=";

    @Override
    public List<ParsingResponseDto> getProjects(String serverId) {
        int requestNum = 0;
        String oAuth2AccessToken = ftTokenRepository.getAccessToken();

        int page = 0;
        List<ParsingResponseDto> allProjects = new ArrayList<>();

        while (true) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + oAuth2AccessToken);
            HttpEntity request = new HttpEntity(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ArrayList> response = null;
            try {
                response = restTemplate.exchange(PROJECT_USER_URL_PREFIX + serverId + PROJECT_USER_URL_SUFFIX + page,
                        HttpMethod.GET, request, ArrayList.class);
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    ftTokenClient.refreshFtToken();
                    oAuth2AccessToken = ftTokenRepository.getAccessToken();
                    continue;
                } else {
                    throw new HttpClientErrorException(e.getStatusCode(), e.getResponseBodyAsString());
                }
            }

            ArrayList<HashMap<String, Object>> body = response.getBody();
            log.info("response getBody: {}", body);
            if (body.isEmpty()) {
                break;
            }

            //allProjects.add(body.toString());
            List<ParsingResponseDto> responseDtoList = parsingData(body);
            allProjects.addAll(responseDtoList);

            page++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.info("thread interrupted!!");
            }

        }

        return allProjects;
    }

    private static List<ParsingResponseDto> parsingData(ArrayList<HashMap<String, Object>> body) {
        List<ParsingResponseDto> responseDtoList = new ArrayList<>();

        for (HashMap<String, Object> data : body) {
            ParsingResponseDto parsingResponseDto = new ParsingResponseDto();

            ProjectDto projectName = extractProjectField(data);
            parsingResponseDto.setProjectName(projectName.getProjectName());

            List<TeamsDto> teams = extractTeamsList(data);
            if (!teams.isEmpty()) {
                TeamsDto lastTeam = teams.get(teams.size() - 1);
                parsingResponseDto.setProjectFinalMark(lastTeam.getProjectFinalMark());
                parsingResponseDto.setProjectStatus(lastTeam.getProjectStatus());
            }

            responseDtoList.add(parsingResponseDto);
        }
        return responseDtoList;
    }

    public static ProjectDto extractProjectField(HashMap<String, Object> data) {
        HashMap<String, Object> project = (HashMap<String, Object>) data.get("project");
        return new ProjectDto((String) project.get("name"));
    }

    public static TeamsDto extractTeamsField(HashMap<String, Object> team) {
        Integer finalMark = (Integer) team.get("final_mark");
        String projectStatus = (String) team.get("status");
        return new TeamsDto(finalMark, projectStatus);
    }

    public static List<TeamsDto> extractTeamsList(HashMap<String, Object> data) {
        List<HashMap<String, Object>> teams = (List<HashMap<String, Object>>) data.get("teams");
        List<TeamsDto> teamsDtoList = new ArrayList<>();

        for (HashMap<String, Object> team : teams) {
            TeamsDto teamsDto = extractTeamsField(team);
            teamsDtoList.add(teamsDto);
        }
        return teamsDtoList;
    }
}
