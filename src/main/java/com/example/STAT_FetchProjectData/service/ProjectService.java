package com.example.STAT_FetchProjectData.service;

import com.example.STAT_FetchProjectData.api.controller.dto.UsersProjectsResponse;
import com.example.STAT_FetchProjectData.client.FtClient;
import com.example.STAT_FetchProjectData.repository.FtTokenRepository;
import com.example.STAT_FetchProjectData.service.dto.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final FtClient ftClient;

    public List<UsersProjectsResponse> getProjects(Users users) {
        List<String> userIds = users.getUserIds();
        List<UsersProjectsResponse> result = new ArrayList<>();

        for (String serverId : userIds) {
            UsersProjectsResponse response = new UsersProjectsResponse();
            response.setServerId(serverId);
            response.setAllProjectsResponse(ftClient.getProjects(serverId));
            result.add(response);
        }
        return result;
    }
}
