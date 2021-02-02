package com.covid.dashboard.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.covid.dashboard.DashboardApplication;
import com.covid.dashboard.data.dto.CovidTimeSeries;
import com.covid.dashboard.data.dto.StateWise;
import com.covid.dashboard.data.entity.User;
import com.covid.dashboard.service.SecurityService;
import com.covid.dashboard.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ DashboardApplication.class })
public class UserControllerUnitTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private SecurityService securityService;
    @MockBean
    private UserService userService;
    @MockBean
    RestTemplate restTemplate;
    private User user;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        user = new User();
        user.setUsername("userName");
        user.setPassword("password");
        user.setPasswordConfirm("password");

        Map<String, Object> dataMap = new HashMap<>();
        List<CovidTimeSeries> casesList = new ArrayList<>();
        CovidTimeSeries c1 = new CovidTimeSeries();
        c1.setDateymd("2020-02-02");
        c1.setTotalconfirmed(100l);
        c1.setTotaldeceased(3l);
        c1.setTotalrecovered(91l);
        c1.setDailyconfirmed(10l);
        c1.setDailydeceased(1l);
        c1.setDailyrecovered(7l);
        casesList.add(c1);

        List<com.covid.dashboard.data.dto.Test> rtpcrTestList = new ArrayList<>();
        com.covid.dashboard.data.dto.Test testData = new com.covid.dashboard.data.dto.Test();
        testData.setDailyrtpcrsamplescollectedicmrapplication(300l);
        rtpcrTestList.add(testData);

        List<StateWise> stateWiseList = new ArrayList<>();
        StateWise stateWise = new StateWise();
        stateWise.setState("Total");
        stateWise.setActive(10l);
        stateWise.setConfirmed(30l);
        stateWise.setDeaths(2l);
        stateWise.setMigratedother(1l);
        stateWise.setRecovered(17l);
        stateWise.setLastupdatedtime("2020-02-02 02:05:56");
        stateWiseList.add(stateWise);

        dataMap.put("cases_time_series", casesList);
        dataMap.put("tested", rtpcrTestList);
        dataMap.put("statewise", stateWiseList);

        ResponseEntity<Map> responseEntity = new ResponseEntity<>(dataMap, HttpStatus.OK);

        Mockito.when(restTemplate.getForEntity(
          Mockito.anyString(),
          ArgumentMatchers.any(Class.class)
        )).thenReturn(responseEntity);
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
          .andExpect(redirectedUrl("/"));
    }

    @DisplayName("Registration Submit Success")
    @Test
    public void registrationSuccess() throws Exception {
        this.mockMvc.perform(post("/registration")
                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                    .param("username", user.getUsername())
                                    .param("password", user.getPassword())
                                    .param("passwordConfirm", user.getPasswordConfirm()))
          .andExpect(status().isFound())
          .andExpect(redirectedUrl("/welcome"));
    }

    @DisplayName("Registration Validation Short Username")
    @Test
    public void registrationValidationShortUsername() throws Exception {
        this.mockMvc.perform(post("/registration")
                                                     .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                     .param("username", "user")
                                                     .param("password", user.getPassword())
                                                     .param("passwordConfirm", user.getPasswordConfirm()))
                                .andExpect(status().isOk())
                                .andExpect(model().attributeHasFieldErrors("userForm"))
                                .andExpect(view().name("registration")).andReturn();
    }

    @DisplayName("Registration Validation Username Exist")
    @Test
    public void registrationValidationUsernameExist() throws Exception {

        when(this.userService.findByUsername(user.getUsername())).thenReturn(user);
        this.mockMvc.perform(post("/registration")
                               .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                               .param("username", user.getUsername())
                               .param("password", user.getPassword())
                               .param("passwordConfirm", user.getPasswordConfirm()))
          .andExpect(status().isOk())
          .andExpect(model().attributeHasFieldErrors("userForm"))
          .andExpect(view().name("registration")).andReturn();
    }

    @DisplayName("Registration Validation Short Password")
    @Test
    public void registrationValidationShortPassword() throws Exception {
        this.mockMvc.perform(post("/registration")
                               .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                               .param("username", user.getUsername())
                               .param("password", "pwd")
                               .param("passwordConfirm", user.getPasswordConfirm()))
          .andExpect(status().isOk())
          .andExpect(model().attributeHasFieldErrors("userForm"))
          .andExpect(view().name("registration")).andReturn();
    }

    @DisplayName("Registration Validation Password Not Match")
    @Test
    public void registrationValidationPasswordNotMatch() throws Exception {
        this.mockMvc.perform(post("/registration")
                               .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                               .param("username", user.getUsername())
                               .param("password", "password1")
                               .param("passwordConfirm", user.getPasswordConfirm()))
          .andExpect(status().isOk())
          .andExpect(model().attributeHasFieldErrors("userForm"))
          .andExpect(view().name("registration")).andReturn();
    }

    @DisplayName("Login Authenticated User")
    @Test
    public void loginAuthenticationSuccess() throws Exception {
        when(this.securityService.isAuthenticated()).thenReturn(true);

        this.mockMvc.perform(get("/login-page"))
          .andExpect(status().isFound())
          .andExpect(redirectedUrl("/"));
    }

    @DisplayName("Login Non-Authenticated User")
    @Test
    public void loginAuthenticationFail() throws Exception {
        when(this.securityService.isAuthenticated()).thenReturn(false);

        this.mockMvc.perform(get("/login-page"))
          .andExpect(status().isOk())
          .andExpect(view().name("login"));
    }

    @DisplayName("Dashboard With Data")
    @Test
    public void dashboardWithData() throws Exception {

        this.mockMvc.perform(get("/welcome"))
          .andExpect(status().isOk())
          .andExpect(view().name("welcome"))
          .andExpect(model().attribute("datav", "Covid-19 Tracker Dashboard"))
          .andExpect(model().attributeExists("covidReport"));
    }
}
