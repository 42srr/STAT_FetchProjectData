package com.example.STAT_FetchProjectData.api.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UsersProjectsResponse {
    String intraId;
    List<String> allProjectsResponse;
}
