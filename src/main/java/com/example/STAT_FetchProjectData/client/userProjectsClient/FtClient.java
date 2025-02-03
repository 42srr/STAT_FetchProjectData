package com.example.STAT_FetchProjectData.client.userProjectsClient;

import com.example.STAT_FetchProjectData.client.dto.ParsingResponseDto;
import java.util.List;

public interface FtClient {
    List<ParsingResponseDto> getUserProjects(String serverId);
}
