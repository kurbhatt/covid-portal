package com.covid.dashboard.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}
