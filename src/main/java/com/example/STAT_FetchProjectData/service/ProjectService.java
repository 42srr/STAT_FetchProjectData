package com.example.STAT_FetchProjectData.service;

import com.example.STAT_FetchProjectData.api.controller.dto.UserContent;
import com.example.STAT_FetchProjectData.api.controller.dto.UsersProjectsResponse;
import com.example.STAT_FetchProjectData.client.projectsClient.ProjectsClient;
import com.example.STAT_FetchProjectData.client.userProjectsClient.FtClient;
import com.example.STAT_FetchProjectData.service.dto.CampusDto;
import com.example.STAT_FetchProjectData.service.dto.ProjectDto;
import com.example.STAT_FetchProjectData.service.dto.Users;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectsClient projectsClient;
    private final FtClient ftClient;

    public List<UsersProjectsResponse> getUserProjects(Users users) {
        List<UserContent> userIds = users.getUserIds();
        List<UsersProjectsResponse> result = new ArrayList<>();

        for (UserContent userId : userIds) {
            String serverId = userId.getServerId();
            UsersProjectsResponse response = new UsersProjectsResponse();
            response.setIntraId(userId.getIntraId());
            response.setAllProjectsResponse(ftClient.getUserProjects(serverId));
            result.add(response);
        }
        return result;
    }

    public List<String> getProjects() {
        Set<String> projectNames = new LinkedHashSet<>();
        List<ProjectDto> allProjectsInfo = projectsClient.fetchAllProjects();

        for (ProjectDto project : allProjectsInfo) {
            if (hasGyeongsan(project)) {
                projectNames.add(project.getName());
            }
        }
        return new ArrayList<>(projectNames);
    }

    private boolean hasGyeongsan(ProjectDto project) {
        if (project.getCampus() == null) {
            return false;
        }

        for (CampusDto campus : project.getCampus()) {
            if ("Gyeongsan".equalsIgnoreCase(campus.getName())) {
                return true;
            }
        }
        return false;
    }
}
