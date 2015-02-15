package com.karmeloz.combinenumbers.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigInteger;

@XmlRootElement(name = "challengesMetaDatas")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChallengeMetaData implements Serializable {

    private Long challengeId;
    private BigInteger senderUserId;
    private Integer difficultyLevel;

    public ChallengeMetaData() {
    }

    public ChallengeMetaData(Long challengeId, BigInteger senderUserId, Integer difficultyLevel) {
        this.challengeId = challengeId;
        this.senderUserId = senderUserId;
        this.difficultyLevel = difficultyLevel;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public BigInteger getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(BigInteger senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    @Override
    public String toString() {
        return "ChallengeMetaData{" +
                "challengeId=" + challengeId +
                ", senderUserId=" + senderUserId +
                ", difficultyLevel=" + difficultyLevel +
                '}';
    }
}
