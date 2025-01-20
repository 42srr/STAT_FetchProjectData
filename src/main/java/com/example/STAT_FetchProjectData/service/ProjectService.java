package com.example.STAT_FetchProjectData.service;

import com.example.STAT_FetchProjectData.api.controller.dto.UserContent;
import com.example.STAT_FetchProjectData.api.controller.dto.UsersProjectsResponse;
import com.example.STAT_FetchProjectData.client.FtClient;
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
        List<UserContent> userIds = users.getUserIds();
        List<UsersProjectsResponse> result = new ArrayList<>();

        for (UserContent userId : userIds) {
            String serverId = userId.getServerId();
            UsersProjectsResponse response = new UsersProjectsResponse();
            response.setIntraId(userId.getIntraId());
            response.setAllProjectsResponse(ftClient.getProjects(serverId));
            result.add(response);
        }
        return result;
    }
}
