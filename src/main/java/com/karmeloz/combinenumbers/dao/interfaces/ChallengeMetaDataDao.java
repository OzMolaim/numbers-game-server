package com.karmeloz.combinenumbers.dao.interfaces;

import com.karmeloz.combinenumbers.dto.ChallengeMetaData;

import java.math.BigInteger;
import java.util.List;

public interface ChallengeMetaDataDao {

    List<ChallengeMetaData> findNotStartedByReceiverId(BigInteger receiverUserId);
}
