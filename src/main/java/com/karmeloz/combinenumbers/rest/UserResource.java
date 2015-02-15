package com.karmeloz.combinenumbers.rest;

import com.karmeloz.combinenumbers.dao.interfaces.UserDataDao;
import com.karmeloz.combinenumbers.entity.UserData;
import com.karmeloz.combinenumbers.rest.interceptor.LoggingInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;

@Stateless
@Path("/user")
@Interceptors(LoggingInterceptor.class)
public class UserResource {

    @EJB
    private UserDataDao userDataDao;

    // TODO: Replace this ugly method, it violates HTTP protocol.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserData createUser(@HeaderParam("user-id") BigInteger userId) {
        userDataDao.createIfNotExists(userId);
        return userDataDao.find(userId);
    }
}
