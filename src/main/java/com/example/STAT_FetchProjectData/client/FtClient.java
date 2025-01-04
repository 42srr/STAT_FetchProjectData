package com.example.STAT_FetchProjectData.client;

import java.util.List;

public interface FtClient {
    List<String> getProjects(String serverId, String oAuth2AccessToken);
}
