package com.hubspot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hubspot.model.Events;
import com.hubspot.model.Session;
import com.hubspot.service.IHubspotApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/hubspot/rest/")
public class HubSpotController {

    @Autowired
    private IHubspotApiService hubspotApiService;

    private List<Events> eventsList;

    private Map<String, List<Session>>  sessionByUser;




    @RequestMapping(value = "/" + "sessions", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getSessionsAndPostSessions() throws JsonProcessingException {
        eventsList = hubspotApiService.getEvents();

        if (CollectionUtils.isEmpty(eventsList)) {
            System.out.println("Unable to get session list information");
            return "Unable to get session list.";
        }

        sessionByUser = hubspotApiService.getSessionByUser(eventsList);
        return hubspotApiService.sendSessions(sessionByUser);
    }

}
