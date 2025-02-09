package com.example.STAT_FetchProjectData.api.controller;

import com.example.STAT_FetchProjectData.api.ApiResponse;
import com.example.STAT_FetchProjectData.api.controller.dto.UsersProjectsResponse;
import com.example.STAT_FetchProjectData.api.controller.dto.UsersRequest;
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

    @PostMapping("/user_projects")
    public ApiResponse<List<UsersProjectsResponse>> getAllUsersProjects(@RequestBody UsersRequest usersRequest) {
        log.info("=== user ===\n{}", usersRequest.toString());
        return ApiResponse.ok(projectService.getUserProjects(usersRequest.usersRequestToUsers()));
    }

    @GetMapping("/projects")
    public ApiResponse<List<String>> getAllProjects() {
        return ApiResponse.ok(projectService.getProjects());
    }
}
