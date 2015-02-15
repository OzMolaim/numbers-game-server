package com.karmeloz.combinenumbers.rest.validator;

import com.karmeloz.combinenumbers.dao.interfaces.UserDataDao;
import com.karmeloz.combinenumbers.entity.Challenge;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

@Stateless
public class ChallengeValidator {

    @EJB
    private UserDataDao userDataDao;

    public void validateSenderAndReceiverExist(Challenge challenge) {
        if (!userDataDao.isUserExists(challenge.getSenderUserId()) ||
                !userDataDao.isUserExists(challenge.getReceiverUserId())) {

            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    public void validateChallengeExistsForUser(Challenge challenge, BigInteger userId) {
        if (challenge == null || !userId.equals(challenge.getReceiverUserId())) {

            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
