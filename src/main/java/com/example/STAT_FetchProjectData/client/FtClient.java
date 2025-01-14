package com.example.STAT_FetchProjectData.client;

import com.example.STAT_FetchProjectData.client.dto.ParsingResponseDto;
import java.util.List;

public interface FtClient {
    List<ParsingResponseDto> getProjects(String serverId);
}
