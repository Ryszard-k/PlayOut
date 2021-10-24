package com.inz.PlayOut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.model.repositories.UserRepo;
import com.inz.PlayOut.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AppUserController.class)
class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private AppUserService appUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<AppUser> appUserList() {
        List<AppUser> appUser = new ArrayList<>();
        appUser.add(new AppUser("Piotr", "password1", "piotr@mail.com"));
        appUser.add(new AppUser("Pawel", "password2", "pawel@mail.com"));
        return appUser;
    }

    @Test
    void findAll() throws Exception {
        when(appUserService.findAll()).thenReturn(appUserList());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/appUser")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = mapper.writeValueAsString(appUserList());

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(actualResponseBody, expectedResponseBody);

        verify(appUserService, times(1)).findAll();
    }

    @Test
    void findAll_emptyRepository() throws Exception {

        when(appUserService.findAll()).thenReturn(List.of());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/appUser")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = "Repository is empty!";

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(actualResponseBody, expectedResponseBody);

        verify(appUserService, times(1)).findAll();
    }

    @Test
    void getById() throws Exception {
        when(appUserService.findById(anyLong())).thenReturn(Optional.ofNullable(appUserList().get(0)));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/appUser/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = mapper.writeValueAsString(Optional.ofNullable(appUserList().get(0)));

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(actualResponseBody, expectedResponseBody);

        verify(appUserService, times(1)).findById(anyLong());
    }

    @Test
    void getById_notFound() throws Exception {
        when(appUserService.findById(anyLong())).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/appUser/{id}", 5)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = "Bad id";

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(expectedResponseBody, actualResponseBody);

        verify(appUserService, times(1)).findById(anyLong());
    }


    @Test
    void add() throws Exception {
        when(appUserService.save(any(AppUser.class))).thenReturn(appUserList().get(0));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/appUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(appUserList().get(0)))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = mapper.writeValueAsString(Optional.ofNullable(appUserList().get(0)));

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(actualResponseBody, expectedResponseBody);

        verify(appUserService, times(1)).save(any(AppUser.class));
    }

    @Test
    void addAppUser_withEmptyFields() throws Exception {
        when(appUserService.save(any(AppUser.class))).thenReturn(appUserList().get(0));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/appUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(null))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        verify(appUserService, times(0)).save(any(AppUser.class));
    }

    @Test
    void delete() throws Exception {
        when(appUserService.findById(anyLong())).thenReturn(Optional.ofNullable(appUserList().get(0)));
        when(appUserService.delete(anyLong())).thenReturn(Optional.of(appUserList().get(0)));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/appUser/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = mapper.writeValueAsString(appUserList().get(0));

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(actualResponseBody, expectedResponseBody);

        verify(appUserService, times(1)).findById(anyLong());
        verify(appUserService, times(1)).delete(anyLong());
    }

    @Test
    void deleteAppUser_withEmptyFields() throws Exception {
        when(appUserService.findById(anyLong())).thenReturn(Optional.empty());
        when(appUserService.delete(anyLong())).thenReturn(Optional.of(appUserList().get(0)));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/appUser/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = "Not found object to delete!";

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals(expectedResponseBody, actualResponseBody);

        verify(appUserService, times(1)).findById(anyLong());
        verify(appUserService, times(0)).delete(anyLong());
    }
}