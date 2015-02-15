package com.karmeloz.combinenumbers.dao.impl;

import com.karmeloz.combinenumbers.dao.interfaces.UserDataDao;
import com.karmeloz.combinenumbers.entity.UserData;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;

@Stateless
public class UserDataDaoImpl implements UserDataDao {

    @PersistenceContext(name = "MysqlPU")
    private EntityManager em;

    @Override
    public UserData find(BigInteger id) {
        return em.find(UserData.class, id);
    }

    @Override
    public void merge(UserData userData) {
        em.merge(userData);
    }

    @Override
    public boolean isUserExists(BigInteger id) {
        return id != null && find(id) != null;
    }

    @Override
    public void createIfNotExists(BigInteger id) {

        if (id == null) {
            throw new IllegalArgumentException("Null user id");
        }

        if (!isUserExists(id)) {
            em.persist(UserData.newUserWithId(id));
        }
    }

    @Override
    public void deleteAll() {
        em.createQuery("DELETE FROM UserData").executeUpdate();
    }
}
