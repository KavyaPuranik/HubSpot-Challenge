package com.hubspot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hubspot.dao.IHubspotDao;
import com.hubspot.model.Events;
import com.hubspot.model.Session;
import com.hubspot.model.SessionByUser;
import com.hubspot.utils.HubspotHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HubspotApiServiceImpl implements IHubspotApiService {

    @Autowired
    private IHubspotDao hubspotDao;

    @Override
    public List<Events> getEvents() {
        List<Events> eventsList = hubspotDao.getEvents();
        return eventsList;
    }

    @Override
    public Map<String, List<Session>>  getSessionByUser(List<Events> eventsList) {
        return HubspotHelper.getSessionByUser(eventsList);
    }

    @Override
    public String sendSessions(Map<String, List<Session>> sessionByUser ) throws JsonProcessingException {
        return hubspotDao.sendSessions(sessionByUser);
    }
}
