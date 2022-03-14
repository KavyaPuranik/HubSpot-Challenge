package com.hubspot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hubspot.model.Events;
import com.hubspot.model.Session;
import com.hubspot.model.SessionByUser;

import java.util.List;
import java.util.Map;

public interface IHubspotApiService {

     List<Events> getEvents();

     Map<String, List<Session>>  getSessionByUser(List<Events> eventsList);

     String sendSessions(Map<String, List<Session>> sessionByUser ) throws JsonProcessingException;

}
