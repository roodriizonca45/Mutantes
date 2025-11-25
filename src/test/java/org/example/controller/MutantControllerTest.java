package org.example.controller;

import org.example.dto.DnaRequest;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService mutantService;

    @Test
    void testMutantEndpoint() throws Exception {
        Mockito.when(mutantService.analyzeDna(Mockito.any()))
                .thenReturn(true);

        String json = """
                {"dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}
                """;

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    void testHumanEndpoint() throws Exception {
        Mockito.when(mutantService.analyzeDna(Mockito.any()))
                .thenReturn(false);

        String json = """
                {"dna": ["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]}
                """;

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isForbidden());
    }

    @Test
    void testInvalidDna() throws Exception {
        String json = """
                {"dna": ["ATG","CAGT"]}
                """;

        mockMvc.perform(
                post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }
}

