package com.karmeloz.combinenumbers.dao.impl;

import com.karmeloz.combinenumbers.dao.interfaces.FriendDifficultyLevelDao;
import com.karmeloz.combinenumbers.dto.FriendDifficultyLevel;
import com.karmeloz.combinenumbers.entity.UserData;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class FriendDifficultyLevelDaoImpl implements FriendDifficultyLevelDao {

    private static final String findUsersDifficultyLevel = "SELECT ud FROM UserData ud WHERE ud.id IN :userIds";

    @PersistenceContext(name = "MysqlPU")
    private EntityManager em;


    @Override
    @SuppressWarnings("unchecked")
    public List<FriendDifficultyLevel> findUsersDifficultyLevel(List<BigInteger> userIds) {

        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<UserData> queryResult = em
                .createQuery(findUsersDifficultyLevel)
                .setParameter("userIds", userIds)
                .getResultList();

        if (queryResult == null || queryResult.isEmpty()) {
            return Collections.emptyList();
        }

        return queryResult.stream()
                .map(ud -> new FriendDifficultyLevel(ud.getId(), ud.getStage()))
                .collect(Collectors.toList());
    }
}
