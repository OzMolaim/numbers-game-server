package com.karmeloz.combinenumbers.dao.interfaces;

import com.karmeloz.combinenumbers.entity.UserData;

import java.math.BigInteger;

public interface UserDataDao {

    UserData find(BigInteger id);
    void merge(UserData userData);
    boolean isUserExists(BigInteger id);
    void createIfNotExists(BigInteger id);
    void deleteAll();
}
