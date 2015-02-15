package com.karmeloz.combinenumbers.dao.impl;

import com.karmeloz.combinenumbers.dao.interfaces.VisitDao;
import com.karmeloz.combinenumbers.entity.Visit;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VisitDaoImpl implements VisitDao {
        
    @PersistenceContext(name = "MysqlPU")
    private EntityManager em;

    @Override
    public void persist(Visit visit) {
        em.persist(visit);
    }

    @Override
    public Long getVisitorsCount() {
        return (Long) em.createQuery("SELECT COUNT(v.id) FROM Visit v").getSingleResult();
    }

    @Override
    public void deleteAll() {
        em.createQuery("DELETE FROM Visit").executeUpdate();
    }
}
