package com.karmeloz.combinenumbers.util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ParseUtil {

    private ParseUtil() {
    }

    public static List<BigInteger> toList(String commaDelimitedIntegers) {
        if (commaDelimitedIntegers == null || commaDelimitedIntegers.isEmpty()) {
            return Collections.emptyList();
        }
        String[] split = commaDelimitedIntegers.split(",");
        return Arrays.stream(split)
                .map(s -> new BigInteger(s.trim()))
                .collect(Collectors.toList());
    }
}
