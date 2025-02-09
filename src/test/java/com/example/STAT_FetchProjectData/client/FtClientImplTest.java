package com.example.STAT_FetchProjectData.client;

import static org.junit.jupiter.api.Assertions.*;

import com.example.STAT_FetchProjectData.client.dto.UserProjectDto;
import com.example.STAT_FetchProjectData.client.dto.TeamsDto;
import com.example.STAT_FetchProjectData.client.userProjectsClient.FtClientImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FtClientImplTest {

    @DisplayName("projectDto객체를 올바르게 반환하는지 확인한다")
    @Test
    void testExtractProjectField() {
        // Given
        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Object> project = new HashMap<>();
        project.put("name", "projectName");
        data.put("project", project);

        // When
        UserProjectDto projectDto = FtClientImpl.extractProjectField(data);

        // Then
        assertNotNull(projectDto);
        assertEquals("projectName", projectDto.getProjectName());
    }

    @DisplayName("teamsDto객체형태의 리스트로 올바르게 반환되는지 확인한다")
    @Test
    void testExtractTeamsList() {
        // Given
        HashMap<String, Object> data = new HashMap<>();
        List<HashMap<String, Object>> teams = new ArrayList<>();

        HashMap<String, Object> team1 = new HashMap<>();
        team1.put("final_mark", 100);
        team1.put("status", "finished");

        HashMap<String, Object> team2 = new HashMap<>();
        team2.put("final_mark", 80);
        team2.put("status", "in_progress");

        teams.add(team1);
        teams.add(team2);
        data.put("teams", teams);

        // When
        List<TeamsDto> teamsDtoList = FtClientImpl.extractTeamsList(data);

        // Then
        assertNotNull(teamsDtoList);
        assertEquals(2, teamsDtoList.size());
        assertEquals(100, teamsDtoList.get(0).getProjectFinalMark());
        assertEquals("finished", teamsDtoList.get(0).getProjectStatus());
        assertEquals(80, teamsDtoList.get(1).getProjectFinalMark());
        assertEquals("in_progress", teamsDtoList.get(1).getProjectStatus());
    }
}