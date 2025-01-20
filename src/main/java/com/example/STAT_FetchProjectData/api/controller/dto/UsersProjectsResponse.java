package com.example.STAT_FetchProjectData.api.controller.dto;

import com.example.STAT_FetchProjectData.client.dto.ParsingResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UsersProjectsResponse {
    String intraId;
    List<ParsingResponseDto> allProjectsResponse;
}
