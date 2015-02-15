package com.karmeloz.combinenumbers.dao.interfaces;

import com.karmeloz.combinenumbers.entity.Visit;

public interface VisitDao {
    
    void persist(Visit visit);
    Long getVisitorsCount();
    void deleteAll();
}
