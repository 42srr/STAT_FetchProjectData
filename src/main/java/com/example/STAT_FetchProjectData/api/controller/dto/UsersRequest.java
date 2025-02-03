package com.example.STAT_FetchProjectData.api.controller.dto;

import com.example.STAT_FetchProjectData.service.dto.Users;
import lombok.Getter;

import java.util.List;

@Getter
public class UsersRequest {
    private List<UserContent> userContents;

    public Users usersRequestToUsers() {
        return new Users(userContents);
    }
}
