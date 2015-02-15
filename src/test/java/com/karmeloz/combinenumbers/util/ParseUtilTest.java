package com.karmeloz.combinenumbers.util;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ParseUtilTest {

    @Test
    public void toList_nullOrEmpty() {
        assertEquals(new ArrayList<BigInteger>(), ParseUtil.toList(null));
        assertEquals(new ArrayList<BigInteger>(), ParseUtil.toList(""));
    }

    @Test
    public void toList_oneInteger() {
        assertEquals(Arrays.asList(BigInteger.TEN), ParseUtil.toList("10"));
        assertEquals(Arrays.asList(BigInteger.TEN), ParseUtil.toList(" 10"));
        assertEquals(Arrays.asList(BigInteger.TEN), ParseUtil.toList("10 "));
        assertEquals(Arrays.asList(BigInteger.TEN), ParseUtil.toList(" 10 "));
    }

    @Test
    public void toList_multipleIntegers() {
        assertEquals(Arrays.asList(BigInteger.TEN, BigInteger.ZERO), ParseUtil.toList("10,0"));
        assertEquals(Arrays.asList(BigInteger.TEN, BigInteger.ZERO, BigInteger.ONE), ParseUtil.toList("10,0,1"));
        assertEquals(Arrays.asList(BigInteger.TEN, BigInteger.ZERO, BigInteger.ONE), ParseUtil.toList(" 10 , 0, 1"));
        assertEquals(Arrays.asList(BigInteger.TEN, BigInteger.ZERO, BigInteger.ONE), ParseUtil.toList(" 10 , 0 , 1   "));
        assertEquals(
                Arrays.asList(BigInteger.TEN, BigInteger.ZERO, BigInteger.ONE, BigInteger.valueOf(2)),
                ParseUtil.toList(" 10 , 0 , 1   ,2")
        );
    }
}