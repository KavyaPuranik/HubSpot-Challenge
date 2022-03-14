package com.hubspot.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.model.Events;
import com.hubspot.model.EventsWrapper;
import com.hubspot.model.Session;
import com.hubspot.model.SessionByUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Repository
public class IHubspotDaoImpl implements IHubspotDao {

    @Value("${hubspot.api.get.session.url}")
    private String getSessionUrl;

    @Value("${hubspot.api.post.session.url}")
    private String postSessionUrl;

    @Override
    public List<Events> getEvents() {
        RestTemplate restTemplate = new RestTemplate();
        EventsWrapper result = restTemplate.getForObject(getSessionUrl, EventsWrapper.class);
        return result.getEvents();
    }

    @Override
    public String sendSessions(Map<String, List<Session>> sessionsByUser) throws JsonProcessingException {
        String response;
        SessionByUser requestBody = new SessionByUser();
        requestBody.setSessionsByUser(sessionsByUser);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody));

        try {
            HttpEntity<SessionByUser> request = new HttpEntity<>(requestBody);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result = restTemplate.postForEntity(postSessionUrl, request, String.class);
            response = result.getStatusCode().toString();

        } catch (HttpClientErrorException ex) {
            System.out.println("Exception status code: " + ex.getStatusCode());
            System.out.println("Exception response body: " + ex.getResponseBodyAsString());
            response = ex.getResponseBodyAsString();
        }
        return response;
    }


}
