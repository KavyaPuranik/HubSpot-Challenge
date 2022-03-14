package com.hubspot.dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hubspot.model.Events;
import com.hubspot.model.Session;

import java.util.List;
import java.util.Map;

public interface IHubspotDao {

    List<Events> getEvents();

    String sendSessions(Map<String, List<Session>> sessionByUser) throws JsonProcessingException;

}
