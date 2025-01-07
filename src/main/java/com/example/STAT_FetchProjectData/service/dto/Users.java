package com.example.STAT_FetchProjectData.service.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class Users {
    private List<String> userIds;

    public Users(List<String> userIds) {
        this.userIds = userIds;
    }
}
