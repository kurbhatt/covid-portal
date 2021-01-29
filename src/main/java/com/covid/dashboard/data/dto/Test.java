package com.covid.dashboard.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Test {
    private String testedasof;
    private Long dailyrtpcrsamplescollectedicmrapplication;

    public String getTestedasof() {
        return testedasof;
    }

    public void setTestedasof(String testedasof) {
        this.testedasof = testedasof;
    }

    public Long getDailyrtpcrsamplescollectedicmrapplication() {
        return dailyrtpcrsamplescollectedicmrapplication;
    }

    public void setDailyrtpcrsamplescollectedicmrapplication(Long dailyrtpcrsamplescollectedicmrapplication) {
        this.dailyrtpcrsamplescollectedicmrapplication = dailyrtpcrsamplescollectedicmrapplication;
    }

    public TimeSeries getTimeSeries() {
        return new TimeSeries(getTestedasof(), getDailyrtpcrsamplescollectedicmrapplication());
    }
}
