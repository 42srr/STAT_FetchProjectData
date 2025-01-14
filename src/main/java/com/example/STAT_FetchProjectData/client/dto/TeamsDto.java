package com.example.STAT_FetchProjectData.client.dto;

import lombok.Data;

@Data
public class TeamsDto {
    private Integer projectFinalMark;
    private String projectStatus;

    public TeamsDto(Integer projectFinalMark, String projectStatus) {
        this.projectFinalMark = projectFinalMark;
        this.projectStatus = projectStatus;
    }
}
