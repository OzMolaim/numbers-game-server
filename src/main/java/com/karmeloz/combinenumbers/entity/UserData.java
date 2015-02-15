package com.karmeloz.combinenumbers.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "user_data")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserData implements Serializable {
    
    @Id
    @XmlElement(name = "userId")
    private BigInteger id;
    private int stage;
    private int surrenderedCount;
    private int solvedCount;
    private int wonCount;
    private int loseCount;
    private long winningPoints;
    private float losingPoints;

    public UserData() {
    }

    public static UserData newUserWithId(BigInteger userId) {
        UserData userData = new UserData();
        userData.setId(userId);
        userData.setStage(1);
        userData.setSurrenderedCount(0);
        userData.setSolvedCount(0);
        userData.setLoseCount(0);
        userData.setWonCount(0);
        userData.setWinningPoints(0L);
        userData.setLosingPoints(0.0F);
        return userData;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getSurrenderedCount() {
        return surrenderedCount;
    }

    public void setSurrenderedCount(int surrenderedCount) {
        this.surrenderedCount = surrenderedCount;
    }

    public long getWinningPoints() {
        return winningPoints;
    }

    public void setWinningPoints(long winningPoints) {
        this.winningPoints = winningPoints;
    }

    public float getLosingPoints() {
        return losingPoints;
    }

    public void setLosingPoints(float losingPoints) {
        this.losingPoints = losingPoints;
    }

    public int getSolvedCount() {
        return solvedCount;
    }

    public void setSolvedCount(int solveCount) {
        this.solvedCount = solveCount;
    }

    public int getWonCount() {
        return wonCount;
    }

    public void setWonCount(int wonCount) {
        this.wonCount = wonCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", stage=" + stage +
                ", surrenderedCount=" + surrenderedCount +
                ", solvedCount=" + solvedCount +
                ", wonCount=" + wonCount +
                ", loseCount=" + loseCount +
                ", winningPoints=" + winningPoints +
                ", losingPoints=" + losingPoints +
                '}';
    }
}
