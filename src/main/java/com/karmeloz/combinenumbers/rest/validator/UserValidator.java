package com.karmeloz.combinenumbers.rest.validator;

import com.karmeloz.combinenumbers.dao.interfaces.UserDataDao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

@Stateless
public class UserValidator {

    @EJB
    private UserDataDao userDataDao;

    public void validateUser(BigInteger userId) {
        if (!userDataDao.isUserExists(userId)) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }
}
