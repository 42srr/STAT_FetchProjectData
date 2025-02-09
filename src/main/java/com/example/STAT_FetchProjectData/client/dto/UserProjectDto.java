package com.example.STAT_FetchProjectData.client.dto;

import lombok.Data;

@Data
public class UserProjectDto {
    private String projectName;

    public UserProjectDto(String projectName) {
        this.projectName = projectName;
    }
}
