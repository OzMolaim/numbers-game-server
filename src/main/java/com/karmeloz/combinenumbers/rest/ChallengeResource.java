package com.karmeloz.combinenumbers.rest;

import com.karmeloz.combinenumbers.dao.interfaces.ChallengeDao;
import com.karmeloz.combinenumbers.dao.interfaces.ChallengeMetaDataDao;
import com.karmeloz.combinenumbers.dao.interfaces.UserDataDao;
import com.karmeloz.combinenumbers.dto.ChallengeMetaData;
import com.karmeloz.combinenumbers.dto.ChallengeStatus;
import com.karmeloz.combinenumbers.entity.Challenge;
import com.karmeloz.combinenumbers.entity.UserData;
import com.karmeloz.combinenumbers.rest.interceptor.LoggingInterceptor;
import com.karmeloz.combinenumbers.rest.validator.ChallengeValidator;
import com.karmeloz.combinenumbers.rest.validator.UserValidator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/challenge")
@Interceptors(LoggingInterceptor.class)
public class ChallengeResource {

    @EJB
    private ChallengeDao challengeDao;
    
    @EJB
    private ChallengeMetaDataDao challengeMetaDataDao;
    
    @EJB
    private UserDataDao userDataDao;

    @EJB
    private UserValidator userValidator;

    @EJB
    private ChallengeValidator challengeValidator;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void challengeReceived(Challenge challenge, @HeaderParam("user-id") BigInteger userId) {
        userValidator.validateUser(userId);
        challengeValidator.validateSenderAndReceiverExist(challenge);

        if (userId.equals(challenge.getSenderUserId())) {
            onSenderPost(challenge);
        }
        else if (userId.equals(challenge.getReceiverUserId())) {
            onReceiverPost(challenge);
        }
        else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    private void onSenderPost(Challenge challenge) {
        persistNewChallenge(challenge);
        UserData userData = userDataDao.find(challenge.getReceiverUserId());
        Challenge dbChallenge = challengeDao.findById(challenge.getId());
        if (surrendered(challenge)) {
            userData.setSurrenderedCount(userData.getSurrenderedCount() + 1);
            userData.setLosingPoints(userData.getLosingPoints() + userData.getStage() * 1.5F);
            dbChallenge.setStatus(ChallengeStatus.SENDER_SURRENDERED);
        }
        else {
            userData.setSolvedCount(userData.getSolvedCount() + 1);
            dbChallenge.setStatus(ChallengeStatus.WAITING);
            challengeDao.merge(dbChallenge);
        }
        userDataDao.merge(userData);
    }

    private void persistNewChallenge(Challenge challenge) {
        Challenge toPersist = convertChallengeForPersistence(challenge);
        challengeDao.persist(toPersist);
    }

    private void onReceiverPost(Challenge challenge) {
        Challenge dbChallenge = challengeDao.findById(challenge.getId());
        UserData receiverUserData = userDataDao.find(challenge.getReceiverUserId());
        UserData senderUserData = userDataDao.find(challenge.getSenderUserId());

        if (surrendered(challenge)) {
            dbChallenge.setStatus(ChallengeStatus.RECEIVER_SURRENDERED);
            updateUserData(challenge, senderUserData, receiverUserData, 1.5F);
        }
        else if (timeOut(challenge)) {
            dbChallenge.setStatus(ChallengeStatus.RECEIVER_TIMED_OUT);
            updateUserData(challenge, senderUserData, receiverUserData, 1F);
        }
        else if (senderWon(challenge, dbChallenge)) {
            dbChallenge.setStatus(ChallengeStatus.SENDER_WON);
            updateUserData(challenge, senderUserData, receiverUserData, 1F);
        }
        else {
            dbChallenge.setStatus(ChallengeStatus.RECEIVER_WON);
            updateUserData(challenge, receiverUserData, senderUserData, 1F);
        }

        userDataDao.merge(receiverUserData);
        userDataDao.merge(senderUserData);
        challengeDao.merge(dbChallenge);
    }

    private boolean senderWon(Challenge receiverChallenge, Challenge dbChallenge) {
        return receiverChallenge.getTimeInSeconds() > dbChallenge.getTimeInSeconds();
    }

    private void updateUserData(Challenge challenge, UserData winningUser, UserData losingUser, float losingFactor) {
        winningUser.setWinningPoints(winningUser.getWinningPoints() + challenge.getDifficultyLevel());
        winningUser.setStage(Math.max(winningUser.getStage(), challenge.getDifficultyLevel() + 1));
        winningUser.setWonCount(winningUser.getWonCount() + 1);

        losingUser.setLosingPoints(losingUser.getLosingPoints() + challenge.getDifficultyLevel() * losingFactor);
        losingUser.setLoseCount(losingUser.getLoseCount() + 1);
    }

    private boolean surrendered(Challenge challenge) {
        return challenge.getTimeInSeconds() == -2;
    }

    private boolean timeOut(Challenge challenge) {
        return challenge.getTimeInSeconds() == -1;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Challenge getChallenge(@HeaderParam("user-id") BigInteger userId,
                                  @QueryParam("challengeId") Long challengeId) {

        userValidator.validateUser(userId);
        Challenge challenge = challengeDao.findById(challengeId);
        challengeValidator.validateChallengeExistsForUser(challenge, userId);
        challengeDao.markAsRequested(challengeId);
        return challengeDao.findById(challengeId);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ChallengeMetaData> getUserChallengesMetaData(@HeaderParam("user-id") BigInteger userId) {
        userValidator.validateUser(userId);
        return challengeMetaDataDao.findNotStartedByReceiverId(userId);
    }

    @PUT
    @Path("/lock")
    @Produces(MediaType.APPLICATION_JSON)
    public String markAsStarted(@HeaderParam("user-id") BigInteger userId,
                                @QueryParam("challengeId") Long challengeId) {
        userValidator.validateUser(userId);
        Challenge challenge = challengeDao.findById(challengeId);
        challengeValidator.validateChallengeExistsForUser(challenge, userId);
        challengeDao.markAsLocked(challengeId);
        return "OK!";
    }

    private Challenge convertChallengeForPersistence(Challenge challenge) {
        Challenge toPersist = new Challenge();
        toPersist.setDifficultyLevel(challenge.getDifficultyLevel());
        toPersist.setNumbers(new ArrayList<>(challenge.getNumbers()));
        toPersist.setDiff(challenge.getDiff());
        toPersist.setGoal(challenge.getGoal());
        toPersist.setTimeInSeconds(challenge.getTimeInSeconds());
        toPersist.setReceiverUserId(challenge.getReceiverUserId());
        toPersist.setSenderUserId(challenge.getSenderUserId());
        toPersist.setSolution(challenge.getSolution());
        return toPersist;
    }
}
