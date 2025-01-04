package com.example.STAT_FetchProjectData.api.controller;

import com.example.STAT_FetchProjectData.api.ApiResponse;
import com.example.STAT_FetchProjectData.api.controller.dto.User;
import com.example.STAT_FetchProjectData.api.controller.exception.ReceiveUserException;
import com.example.STAT_FetchProjectData.client.FtClientImpl;
import com.example.STAT_FetchProjectData.client.FtClientToken;
import com.example.STAT_FetchProjectData.client.FtClientTokenImpl;
import com.example.STAT_FetchProjectData.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final FtClientTokenImpl ftClientToken;

    @GetMapping("/project")
    public ApiResponse<List<String>> getAllProjects(@RequestParam String serverId) {
        log.info("=== user ===\n{}", serverId);
        if (serverId == null)
            throw new ReceiveUserException("Didn't receive user");

        String oAuth2AccessToken = ftClientToken.getAccessToken();
        log.info("Controller Token : {}\n", oAuth2AccessToken);

        return ApiResponse.ok(projectService.getProjects(serverId, oAuth2AccessToken));
    }
}
