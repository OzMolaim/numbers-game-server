package com.karmeloz.combinenumbers.rest;

import com.karmeloz.combinenumbers.dao.interfaces.FriendDifficultyLevelDao;
import com.karmeloz.combinenumbers.dto.FriendDifficultyLevel;
import com.karmeloz.combinenumbers.rest.interceptor.LoggingInterceptor;
import com.karmeloz.combinenumbers.rest.validator.UserValidator;
import com.karmeloz.combinenumbers.util.ParseUtil;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.util.List;

@Stateless
@Path("/friend")
@Interceptors(LoggingInterceptor.class)
public class FriendDifficultyLevelResource {

    @EJB
    private UserValidator userValidator;

    @EJB
    private FriendDifficultyLevelDao friendDifficultyLevelDao;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<FriendDifficultyLevel> friendDifficultyLevelRequest(@HeaderParam("friend-ids") String friendIds,
                                                                    @HeaderParam("user-id") BigInteger userId) {

        userValidator.validateUser(userId);
        List<BigInteger> ids = ParseUtil.toList(friendIds);
        return friendDifficultyLevelDao.findUsersDifficultyLevel(ids);
    }
}
