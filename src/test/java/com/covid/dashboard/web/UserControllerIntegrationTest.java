package com.covid.dashboard.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.covid.dashboard.DashboardApplication;
import com.covid.dashboard.data.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DashboardApplication.class)
@WebAppConfiguration
class UserControllerIntegrationTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private UserController userController;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        user = new User();
        user.setUsername("userName");
        user.setPassword("password");
        user.setPasswordConfirm("password");
    }


    @DisplayName("Registration View")
    @Test
    void registrationPage() throws Exception {
        this.getMockMvc().perform(get("/registration"))
          .andExpect(status().isOk())
          .andExpect(view().name("registration"));
    }

    @DisplayName("Registration Form Submit")
    @Test
    void testRegistration() throws Exception {
        this.getMockMvc().perform(post("/registration")
                                  .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                  .param("username", user.getUsername())
                                  .param("password", user.getPassword())
                                  .param("passwordConfirm", user.getPasswordConfirm()))
          .andExpect(status().isFound())
          .andExpect(redirectedUrl("/welcome"));
    }

    @DisplayName("Login View")
    @Test
    void loginPage() throws Exception {
        this.getMockMvc().perform(get("/login-page"))
          .andExpect(status().isOk())
          .andExpect(view().name("login"));
    }

    @DisplayName("Dashboard View")
    @Test
    void welcomePage() throws Exception {
        this.getMockMvc().perform(get("/welcome"))
          .andExpect(status().isOk())
          .andExpect(view().name("welcome"))
          .andExpect(model().attribute("datav", "Covid-19 Tracker Dashboard"));
    }

    private MockMvc getMockMvc(){
        if (this.mockMvc == null) {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }
        return mockMvc;
    }
}