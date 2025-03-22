package com.imdbclone.tvshow.controller;

import com.imdbclone.tvshow.service.api.IAppLogService;
import com.imdbclone.tvshow.service.api.ITVShowService;
import com.imdbclone.tvshow.web.response.TVShowResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
public class TVShowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ITVShowService tvShowService;

    @MockitoBean
    private IAppLogService appLogService;

    @Test
    void getTVShowByIdTest() throws Exception {
        // Given: Create a mock TVShowResponse
        TVShowResponse mockResponse = TVShowResponse.builder()
                .id(3L)
                .title("Friends 2")
                .language("Japanese")
                .description("A thrilling drama.")
                .build();
        // Mock service behavior
        when(tvShowService.getTVShowById(3L)).thenReturn(mockResponse);

        // When: Perform GET request
        mockMvc.perform(get("/tv-shows/3") // Adjust URL as per your API
                        .contentType(MediaType.APPLICATION_JSON)) // Request JSON response
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.id").value(3L)) // Validate JSON response
                .andExpect(jsonPath("$.title").value("Friends 2"))
                .andExpect(jsonPath("$.language").value("Japanese"))
                .andExpect(jsonPath("$.description").value("A thrilling drama."));

        // Verify service was called once
        verify(tvShowService, times(1)).getTVShowById(3L);
    }

}
