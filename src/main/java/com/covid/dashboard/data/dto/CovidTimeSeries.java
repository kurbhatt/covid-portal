package com.covid.dashboard.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CovidTimeSeries {

    private String dateymd;
    private Long totalconfirmed;
    private Long totaldeceased;
    private Long totalrecovered;
    private Long dailyconfirmed;
    private Long dailydeceased;
    private Long dailyrecovered;

    public String getDateymd() {
        return dateymd;
    }

    public void setDateymd(String dateymd) {
        this.dateymd = dateymd;
    }

    public Long getTotalconfirmed() {
        return totalconfirmed;
    }

    public void setTotalconfirmed(Long totalconfirmed) {
        this.totalconfirmed = totalconfirmed;
    }

    public Long getTotaldeceased() {
        return totaldeceased;
    }

    public void setTotaldeceased(Long totaldeceased) {
        this.totaldeceased = totaldeceased;
    }

    public Long getTotalrecovered() {
        return totalrecovered;
    }

    public void setTotalrecovered(Long totalrecovered) {
        this.totalrecovered = totalrecovered;
    }

    public Long getDailyconfirmed() {
        return dailyconfirmed;
    }

    public void setDailyconfirmed(Long dailyconfirmed) {
        this.dailyconfirmed = dailyconfirmed;
    }

    public Long getDailydeceased() {
        return dailydeceased;
    }

    private void setDailydeceased(Long dailydeceased) {
        this.dailydeceased = dailydeceased;
    }

    private Long getDailyrecovered() {
        return dailyrecovered;
    }

    public void setDailyrecovered(Long dailyrecovered) {
        this.dailyrecovered = dailyrecovered;
    }

    public TimeSeries getTimeSeries() {
        return new TimeSeries(getDateymd(), getDailyconfirmed());
    }
}
