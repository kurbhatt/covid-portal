package com.covid.dashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.covid.dashboard.data.dto.CovidTimeSeries;
import com.covid.dashboard.data.dto.Report;
import com.covid.dashboard.data.dto.StateWise;
import com.covid.dashboard.data.dto.Test;
import com.covid.dashboard.data.dto.TimeSeries;
import com.covid.dashboard.util.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DashboardService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public Report getTimeSeriesData() {
        Report report = new Report();
        try {
            ResponseEntity<Map> responseEntity = this.restTemplate.getForEntity(Constants.COVID19_BASE_URL + Constants.TIME_SERIES_URL, Map.class);
            Map<String, Object> body = responseEntity.getBody();
            if (body != null) {
                if (body.containsKey("cases_time_series")) {
                    Object cases_time_series = body.get("cases_time_series");
                    if (cases_time_series != null) {
                        List<CovidTimeSeries> casesList = this.objectMapper.readValue(
                          this.objectMapper.writeValueAsString(cases_time_series), new TypeReference<List<CovidTimeSeries>>() {
                        });
                        List<TimeSeries> timeSeries = new ArrayList<>();
                        for (int i = 0; i < casesList.size(); i++) {
                            timeSeries.add(casesList.get(i).getTimeSeries());
                        }
                        report.setTimeSeries(timeSeries);
                    }
                }
                if (body.containsKey("tested")) {
                    Object tested = body.get("tested");
                    if (tested != null) {
                        List<Test> rtpcrTestList = this.objectMapper.readValue(
                          this.objectMapper.writeValueAsString(tested), new TypeReference<List<Test>>() {
                          });
                        List<TimeSeries> timeSeries = new ArrayList<>();
                        for (int i = 0; i < rtpcrTestList.size(); i++) {
                            if (!StringUtils.isEmpty(rtpcrTestList.get(i).getDailyrtpcrsamplescollectedicmrapplication())) {
                                timeSeries.add(rtpcrTestList.get(i).getTimeSeries());
                            }
                        }
                        report.setRtpcrTests(timeSeries);
                    }
                }
                if (body.containsKey("statewise")) {
                    Object statewise = body.get("statewise");
                    if (statewise != null) {
                        List<StateWise> stateWiseList = this.objectMapper.readValue(
                          this.objectMapper.writeValueAsString(statewise), new TypeReference<List<StateWise>>() {
                        });
                        if (stateWiseList != null && !stateWiseList.isEmpty()) {
                            StateWise stateWise = stateWiseList.get(0);
                            if (stateWise != null && stateWise.getState().equals("Total")) {
                                report.setActive(stateWise.getActive());
                                report.setConfirmed(stateWise.getConfirmed());
                                report.setRecovered(stateWise.getRecovered());
                                report.setDeaths(stateWise.getDeaths());
                                report.setMigratedother(stateWise.getMigratedother());
                                report.setLastupdatedtime(stateWise.getLastupdatedtime());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }
}
