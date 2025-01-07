package com.example.STAT_FetchProjectData.api.controller.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UsersRequest {
    private List<String> userIds;
}

/**
 *
 * {
 *     "userIds" : [18213, 1232727]
 *
 * }
 */
