package com.hubspot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class SessionByUser {


    public Map<String, List<Session>> getSessionsByUser() {
        return sessionsByUser;
    }

    public void setSessionsByUser(Map<String, List<Session>> sessionsByUser) {
        this.sessionsByUser = sessionsByUser;
    }

    @JsonProperty("sessionsByUser")
    Map<String, List<Session>> sessionsByUser;
}
