package com.example.STAT_FetchProjectData.client.dto;

import lombok.Data;

@Data
public class ParsingResponseDto {
    private String projectName;
    private Integer projectFinalMark;
    private String projectStatus;
}
