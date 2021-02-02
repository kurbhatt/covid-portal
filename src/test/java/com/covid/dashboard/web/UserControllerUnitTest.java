package com.covid.dashboard.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.covid.dashboard.DashboardApplication;
import com.covid.dashboard.service.SecurityService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ DashboardApplication.class })
public class UserControllerUnitTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SecurityService securityService;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("Registration Non-Authenticated User")
    @Test
    public void registrationAuthenticationFail() throws Exception {
        when(this.securityService.isAuthenticated()).thenReturn(false);

        this.mockMvc.perform(get("/registration"))
          .andExpect(status().isOk())
          .andExpect(view().name("registration"));
    }

    @DisplayName("Registration Authenticated User")
    @Test
    public void registrationAuthenticationSuccess() throws Exception {
        when(this.securityService.isAuthenticated()).thenReturn(true);

        this.mockMvc.perform(get("/registration"))
          .andExpect(status().isFound())
          .andExpect(view().name("redirect:/"));
    }
}
