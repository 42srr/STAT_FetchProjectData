package com.example.STAT_FetchProjectData.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.STAT_FetchProjectData.client.projectsClient.ProjectsClient;
import com.example.STAT_FetchProjectData.service.dto.CampusDto;
import com.example.STAT_FetchProjectData.service.dto.ProjectDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectsClient projectsClient;

    @InjectMocks
    private ProjectService projectService;

    @DisplayName("경산이 있는 프로젝트만 제대로 담기는지 확인한다.")
    @Test
    void getProjectInGyeongsanWithCorrectList() {
        // Given
        List<ProjectDto> allProjects = List.of(
                new ProjectDto("project1", List.of(new CampusDto("Gyeongsan"))),
                new ProjectDto("project2", List.of(new CampusDto("Seoul"))),
                new ProjectDto("project3", List.of(new CampusDto("Gyeongsan")))
        );

        when(projectsClient.fetchAllProjects()).thenReturn(allProjects);
        // When
        List<String> tempResult = projectService.getProjects();
        // Then
        assertEquals(2, tempResult.size());
        assertTrue(tempResult.contains("project1"));
        assertFalse(tempResult.contains("project2"));
        assertTrue(tempResult.contains("project3"));
    }

    @DisplayName("경산에 있는 프로젝트가 없을 때 빈 리스트를 반환한다.")
    @Test
    void getEmptyListWithoutGyeongsan() {
        // Given
        List<ProjectDto> allProjects = List.of(
                new ProjectDto("project1", List.of(new CampusDto("Seoul"))),
                new ProjectDto("project2", List.of(new CampusDto("Seoul")))
        );

        when(projectsClient.fetchAllProjects()).thenReturn(List.of());
        // When
        List<String> tempResult = projectService.getProjects();
        // Then
        assertTrue(tempResult.isEmpty());
    }

    @DisplayName("마지막 다음 페이지가 왔을 때, 올바르게 종료된다.")
    @Test
    void exitWithLastPage() {
        // Given
        List<ProjectDto> page1 = List.of();

        when(projectsClient.fetchAllProjects()).thenReturn(page1);
        // When
        List<String> tempResult = projectService.getProjects();
        // Then
        assertEquals(0, tempResult.size());
    }
}