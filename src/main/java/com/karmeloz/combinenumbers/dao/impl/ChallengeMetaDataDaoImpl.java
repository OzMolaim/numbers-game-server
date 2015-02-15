package com.karmeloz.combinenumbers.dao.impl;

import com.karmeloz.combinenumbers.dao.interfaces.ChallengeMetaDataDao;
import com.karmeloz.combinenumbers.dto.ChallengeMetaData;
import com.karmeloz.combinenumbers.dto.ChallengeStatus;
import com.karmeloz.combinenumbers.entity.Challenge;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Stateless
public class ChallengeMetaDataDaoImpl implements ChallengeMetaDataDao {

    private static final String findBySenderId = "SELECT c FROM Challenge c " +
            "WHERE c.receiverUserId = :recId AND (c.status = :witStatus OR (c.status = :reqStatus AND c.lastRequested < :validRequestTime))";
    
    @PersistenceContext(name = "MysqlPU")
    private EntityManager em;
        
    @Override
    public List<ChallengeMetaData> findNotStartedByReceiverId(BigInteger receiverUserId) {
        long validRequestTime = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(2);
        List<Challenge> resultList = em
                .createQuery(findBySenderId, Challenge.class)
                .setParameter("recId", receiverUserId)
                .setParameter("witStatus", ChallengeStatus.WAITING)
                .setParameter("reqStatus", ChallengeStatus.REQUESTED)
                .setParameter("validRequestTime", validRequestTime)
                .getResultList();
        return convertMetaDataList(resultList);
    }

    private List<ChallengeMetaData> convertMetaDataList(List<Challenge> resultList) {
        List<ChallengeMetaData> result = new ArrayList<>();
        if (resultList != null) {
            resultList.stream().forEach((c) ->
                    result.add(new ChallengeMetaData(c.getId(), c.getSenderUserId(), c.getDifficultyLevel())));
        }
        return result;
    }
}
