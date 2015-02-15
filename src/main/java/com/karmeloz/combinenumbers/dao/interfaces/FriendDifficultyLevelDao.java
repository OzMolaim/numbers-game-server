package com.karmeloz.combinenumbers.dao.interfaces;

import com.karmeloz.combinenumbers.dto.FriendDifficultyLevel;

import java.math.BigInteger;
import java.util.List;

public interface FriendDifficultyLevelDao {

    List<FriendDifficultyLevel> findUsersDifficultyLevel(List<BigInteger> userIds);
}
