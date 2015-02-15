package com.karmeloz.combinenumbers.dao.interfaces;

import com.karmeloz.combinenumbers.entity.Challenge;

public interface ChallengeDao {
    
    void persist(Challenge challenge);
    Challenge findById(long challengeId);
    void deleteAll();
    void markAsRequested(long challengeId);
    void markAsLocked(long challengeId);
    void merge(Challenge challenge);
}
