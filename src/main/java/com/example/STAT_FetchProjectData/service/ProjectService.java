package com.example.STAT_FetchProjectData.service;

import com.example.STAT_FetchProjectData.client.FtClientImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final FtClientImpl ftClient;

    public List<String> getProjects(String serverId, String oAuth2AccessToken) {
        /*
        todo
        없는 유저 들어왔을 경우 처리
         */
        return ftClient.getProjects(serverId, oAuth2AccessToken);
    }
}
