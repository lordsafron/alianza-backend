package com.hm.alianza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hm.alianza.application.IClientApp;
import com.hm.alianza.common.Constants;
import com.hm.alianza.dto.ClientDto;
import com.hm.alianza.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
class ClientControllerTest {

    @MockBean
    private IClientApp iClientApp;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createClient() throws Exception {
        var dto =
                new ClientDto(1L, null, "pedro perez", "pperez@gmail.com", "123345123", null);
        when(iClientApp.save(dto))
                .thenReturn(new Response<>(Constants.SUCCESS, "1231", dto));

        mvc.perform(
                        MockMvcRequestBuilders.post("/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(dto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createClient_badRequest_by_businessId() throws Exception {
        var dto =
                new ClientDto(1L, null, null, "pperez@gmail.com", "123345123", null);
        when(iClientApp.save(dto))
                .thenReturn(new Response<>(Constants.SUCCESS, "1231", dto));

        mvc.perform(
                        MockMvcRequestBuilders.post("/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(dto))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void createClient_badRequest_by_email() throws Exception {
        var dto =
                new ClientDto(1L, null, "pedro perez", null, "123345123", null);
        when(iClientApp.save(dto))
                .thenReturn(new Response<>(Constants.SUCCESS, "1231", dto));

        mvc.perform(
                        MockMvcRequestBuilders.post("/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(dto))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void findBySharedId() throws Exception {
        var sharedId = "pperez";
        var dto =
                new ClientDto(1L, "pperez", "pedro perez", "pperez@gmail.com", "123345123", null);
        when(iClientApp.findBySharedId(sharedId))
                .thenReturn(new Response<>(Constants.SUCCESS, "1231", dto));

        mvc.perform(MockMvcRequestBuilders.get("/client/{sharedId}", sharedId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findBySharedId_throw_bad_request() throws Exception {
        var sharedId = "pperez1";

        mvc.perform(MockMvcRequestBuilders.get("/client/{sharedId}", sharedId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAll() throws Exception {
        when(iClientApp.findAll())
                .thenReturn(
                        new Response<>(Constants.SUCCESS,
                                "1231",
                                Arrays.asList(new ClientDto(), new ClientDto()))
                );

        mvc.perform(MockMvcRequestBuilders.get("/client")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}