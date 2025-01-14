package com.example.STAT_FetchProjectData.client.dto;

import lombok.Data;

@Data
public class ProjectDto {
    private String projectName;

    public ProjectDto(String projectName) {
        this.projectName = projectName;
    }
}
