package com.karmeloz.combinenumbers.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;

@XmlRootElement(name = "friendsDifficultyLevels")
@XmlAccessorType(XmlAccessType.FIELD)
public class FriendDifficultyLevel {

    private BigInteger userId;
    private int stage;

    public FriendDifficultyLevel() {
    }

    public FriendDifficultyLevel(BigInteger userId, int stage) {
        this.userId = userId;
        this.stage = stage;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "FriendDifficultyLevel{" +
                "userId=" + userId +
                ", stage=" + stage +
                '}';
    }
}
