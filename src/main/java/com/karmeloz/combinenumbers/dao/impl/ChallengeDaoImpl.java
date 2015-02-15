package com.karmeloz.combinenumbers.dao.impl;

import com.karmeloz.combinenumbers.dao.interfaces.ChallengeDao;
import com.karmeloz.combinenumbers.dto.ChallengeStatus;
import com.karmeloz.combinenumbers.entity.Challenge;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ChallengeDaoImpl implements ChallengeDao {

    @PersistenceContext(name = "MysqlPU")
    private EntityManager em;
        
    @Override
    public void persist(Challenge challenge) {
        em.persist(challenge);
    }

    @Override
    public Challenge findById(long challengeId) {
        return em.find(Challenge.class, challengeId);
    }

    @Override
    public void deleteAll() {
        em.createNativeQuery("DELETE FROM challenge_numbers").executeUpdate();
        em.createQuery("DELETE FROM Challenge").executeUpdate();
    }

    @Override
    public void markAsRequested(long challengeId) {
        Challenge challenge = findById(challengeId);
        challenge.setStatus(ChallengeStatus.REQUESTED);
        challenge.setLastRequested(System.currentTimeMillis());
        merge(challenge);
    }

    @Override
    public void markAsLocked(long challengeId) {
        Challenge challenge = findById(challengeId);
        challenge.setStatus(ChallengeStatus.LOCKED);
        merge(challenge);
    }

    @Override
    public void merge(Challenge challenge) {
        em.merge(challenge);
    }
}
