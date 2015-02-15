package com.karmeloz.combinenumbers.rest;

import com.karmeloz.combinenumbers.dao.interfaces.VisitDao;
import com.karmeloz.combinenumbers.entity.Visit;
import com.karmeloz.combinenumbers.rest.interceptor.LoggingInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Stateless
@Path("/")
@Interceptors(LoggingInterceptor.class)
public class WelcomeResource {

    @EJB
    private VisitDao visitDao;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String welcomeMessage(@Context HttpServletRequest req) {
        Date current = new Date();
        String address = extractSrcAddress(req);
        Visit visit = new Visit(current, address);
        visitDao.persist(visit);
        return makeWelcomeMessage(visit);
    }
    
    private String extractSrcAddress(HttpServletRequest req) {
        String remoteHost = req.getRemoteHost();
        String remoteAddr = req.getRemoteAddr();
        int remotePort = req.getRemotePort();
        return String.format("%s (%s:%s)", remoteHost, remoteAddr, remotePort);
    }
    
    private String makeWelcomeMessage(Visit visit) {
        return String.format("%s%n%s: %s%n%s: %s%n%s: %s%n%s", 
                  "Welcome to Combine-the-Numbers REST API.",
                  "Server Time is",
                  visit.getVisitDate(),
                  "Your Address is",
                  visit.getSrcAddress(),
                  "You are Visitor Number",
                  visitDao.getVisitorsCount(),
                  "Internal Data: " + this.hashCode()
        );  
    }
}
