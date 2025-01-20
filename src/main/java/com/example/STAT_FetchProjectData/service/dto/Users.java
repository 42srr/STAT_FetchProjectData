package com.example.STAT_FetchProjectData.service.dto;

import com.example.STAT_FetchProjectData.api.controller.dto.UserContent;
import lombok.Getter;

import java.util.List;

@Getter
public class Users {
    private List<UserContent> userIds;

    public Users(List<UserContent> userIds) {
        this.userIds = userIds;
    }
}
