package com.example.STAT_FetchProjectData.api.controller;

import com.example.STAT_FetchProjectData.api.ApiResponse;
import com.example.STAT_FetchProjectData.api.controller.dto.UsersProjectsResponse;
import com.example.STAT_FetchProjectData.api.controller.dto.UsersRequest;
import com.example.STAT_FetchProjectData.repository.FtMemoryTokenRepository;
import com.example.STAT_FetchProjectData.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final FtMemoryTokenRepository ftClientToken;

    @PostMapping("/project")
    public ApiResponse<List<UsersProjectsResponse>> getAllProjects(@RequestBody UsersRequest usersRequest) {
        log.info("=== user ===\n{}", usersRequest.toString());

        String oAuth2AccessToken = ftClientToken.getAccessToken();
        log.info("Controller Token : {}\n", oAuth2AccessToken);

        List<UsersProjectsResponse> result = new ArrayList<>();
        List<String> users = usersRequest.getUserIds();
        for (String serverId : users) {
            UsersProjectsResponse response = new UsersProjectsResponse();
            response.setServerId(serverId);
            response.setAllProjectsResponse(projectService.getProjects(serverId, oAuth2AccessToken));
            result.add(response);
        }

        return ApiResponse.ok(result);
    }
}
