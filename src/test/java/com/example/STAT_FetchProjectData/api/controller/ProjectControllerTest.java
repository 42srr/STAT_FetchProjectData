package com.example.STAT_FetchProjectData.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.STAT_FetchProjectData.service.ProjectService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @DisplayName("/projects를 했을 경우 정상 응답되는지 확인한다.")
    @Test
    void getAllProjects_WithCorrectEndPoints() throws Exception {
        // Given
        List<String> tempProjects = List.of("project1", "project2", "project3");
        when(projectService.getProjects()).thenReturn(tempProjects);
        // When & Then
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0]").value("project1"))
                .andExpect(jsonPath("$.data[1]").value("project2"))
                .andExpect(jsonPath("$.data[2]").value("project3"))
                .andExpect(jsonPath("$.data.length()").value(3));
    }

    @DisplayName("잘못된 endpoint요청 시 에러를 반환한다.")
    @Test
    void getAllProjects_WithInvalidEndPoints() throws Exception {
        mockMvc.perform(get("/invalidEndpoints"))
                .andExpect(status().isNotFound());

    }
}
