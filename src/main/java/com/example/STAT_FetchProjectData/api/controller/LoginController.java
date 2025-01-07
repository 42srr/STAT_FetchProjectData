package com.example.STAT_FetchProjectData.api.controller;

import com.example.STAT_FetchProjectData.api.ApiResponse;
import com.example.STAT_FetchProjectData.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public ApiResponse<String> doLogin(
            @RequestParam(required = true) String code ) {
        log.info("=== code ===\n{}", code);

        return ApiResponse.ok(loginService.registerAccessToken(code));
    }
}
