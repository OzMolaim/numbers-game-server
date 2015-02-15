package com.karmeloz.combinenumbers.entity;

import com.karmeloz.combinenumbers.dto.ChallengeStatus;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Challenge implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @XmlElement(name = "challengeId")
    private long id;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private ArrayList<Long> numbers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    private Integer difficultyLevel;
    private Long goal;
    private Long diff;
    private Integer timeInSeconds;
    private BigInteger receiverUserId;
    private BigInteger senderUserId;
    private String solution;
    private long lastRequested;

    public Challenge() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer level) {
        this.difficultyLevel = level;
    }

    public ArrayList<Long> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<Long> numbers) {
        this.numbers = numbers;
    }

    public ChallengeStatus getStatus() {
        return status;
    }

    public void setStatus(ChallengeStatus status) {
        this.status = status;
    }

    public Long getGoal() {
        return goal;
    }

    public void setGoal(Long goal) {
        this.goal = goal;
    }

    public Long getDiff() {
        return diff;
    }

    public void setDiff(Long diff) {
        this.diff = diff;
    }

    public Integer getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(Integer timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public BigInteger getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(BigInteger receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public BigInteger getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(BigInteger senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public long getLastRequested() {
        return lastRequested;
    }

    public void setLastRequested(long lastRequested) {
        this.lastRequested = lastRequested;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "id=" + id +
                ", numbers=" + numbers +
                ", status=" + status +
                ", difficultyLevel=" + difficultyLevel +
                ", goal=" + goal +
                ", diff=" + diff +
                ", timeInSeconds=" + timeInSeconds +
                ", receiverUserId=" + receiverUserId +
                ", senderUserId=" + senderUserId +
                ", solution='" + solution + '\'' +
                ", lastRequested=" + lastRequested +
                '}';
    }
}
