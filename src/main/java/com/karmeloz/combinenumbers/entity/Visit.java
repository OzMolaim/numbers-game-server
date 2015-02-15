package com.karmeloz.combinenumbers.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Visit implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date visitDate;

    @Column(nullable = false)
    private String srcAddress;
    
    public Visit() {
    }

    public Visit(Date visitDate, String srcAddress) {
        this.visitDate = visitDate;
        this.srcAddress = srcAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getSrcAddress() {
        return srcAddress;
    }

    public void setSrcAddress(String srcAddress) {
        this.srcAddress = srcAddress;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", visitDate=" + visitDate +
                ", srcAddress='" + srcAddress + '\'' +
                '}';
    }
}
