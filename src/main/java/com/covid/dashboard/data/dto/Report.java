package com.covid.dashboard.data.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Report extends StateWise {

    private String country;
    private List<TimeSeries> timeSeries;
    private List<TimeSeries> rtpcrTests;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<TimeSeries> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(List<TimeSeries> timeSeries) {
        this.timeSeries = timeSeries;
    }

    public List<TimeSeries> getRtpcrTests() {
        return rtpcrTests;
    }

    public void setRtpcrTests(List<TimeSeries> rtpcrTests) {
        this.rtpcrTests = rtpcrTests;
    }
}
