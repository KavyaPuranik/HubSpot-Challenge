package com.hubspot.utils;


import com.hubspot.model.Events;
import com.hubspot.model.Session;

import java.util.*;


public class HubspotHelper {



    public static Map<String, List<Session>>  getSessionByUser(List<Events> eventsList) {

        Map<String, List<Events>> eventsMap = new HashMap<>();
        for(Events event : eventsList) {
            List<Events> res = eventsMap.getOrDefault(event.getVisitorId(), new ArrayList<>());
            res.add(event);
            eventsMap.put(event.getVisitorId(), res);
        }

        for(Map.Entry<String, List<Events>> entry : eventsMap.entrySet()) {
            Collections.sort(entry.getValue(), (a,b) -> (int)(a.getTimestamp()-b.getTimestamp()));
        }


        Map<String , List<Session>> sessionMap = new HashMap<>();
        for(Map.Entry<String, List<Events>> entry : eventsMap.entrySet()) {

            List<Events> list = entry.getValue();
            int lastIndex = 0;
            for(int i = 0; i<list.size(); i++) {
                if(i == 0) {
                    Session session = new Session();
                    session.setDuration(0);
                    List<String> pages = new ArrayList<>();
                    pages.add(list.get(i).getUrl());
                    session.setPages(pages);
                    session.setStartTime(list.get(i).getTimestamp());
                    List<Session> sessionList = new ArrayList<>();
                    sessionList.add(session);
                    sessionMap.put(entry.getKey(), sessionList);
                }else {
                    if((list.get(i).getTimestamp() - list.get(i-1).getTimestamp()) < 600000) {
                        List<Session> sessionList = sessionMap.get(entry.getKey());
                        int last = sessionList.size()-1;
                        Session lastSession = sessionList.get(lastIndex);

                        lastSession.setDuration(lastSession.getDuration()+ list.get(i).getTimestamp() - list.get(i-1).getTimestamp());
                        List<String> pages = lastSession.getPages();
                        pages.add(list.get(i).getUrl());
                        lastSession.setPages(pages);

                   }else {
                        Session session = new Session();
                        session.setDuration(0);
                        List<String> pages = new ArrayList<>();
                        pages.add(list.get(i).getUrl());
                        session.setPages(pages);
                        session.setStartTime(list.get(i).getTimestamp());
                        List<Session> sessionList = sessionMap.get(entry.getKey());
                        sessionList.add(session);
                        sessionMap.put(entry.getKey(), sessionList);
                        lastIndex++;
                    }
                }
            }

        }

        for(Map.Entry<String, List<Session>> entry : sessionMap.entrySet()) {
           Collections.sort(entry.getValue(), (a,b) ->(int)(a.getStartTime()-b.getStartTime()));
        }

        for(Map.Entry<String, List<Session>> entry : sessionMap.entrySet()) {
            for(Session session : entry.getValue()) {
                Collections.sort(session.getPages(), (a,b) -> a.compareTo(b));
            }
        }


//        for(Map.Entry<String, List<Session>> entry : sessionMap.entrySet()) {
//            System.out.print(entry.getKey()+ " ");
//            for(Session session : entry.getValue()) {
//                System.out.print(session.getDuration() + " " + session.getStartTime());
//                for(String list : session.getPages()) {
//                    System.out.print(list + " ");
//                }
//            }
//            System.out.println("***************");
//        }


        return sessionMap;

    }

}
