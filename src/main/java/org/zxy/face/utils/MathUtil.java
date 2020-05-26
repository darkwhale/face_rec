package org.zxy.face.utils;

import java.math.BigDecimal;

public class MathUtil {

    public static String add(String numStr){
        if (numStr.equals("")) {
            return "1";
        }
        return String.valueOf(new BigDecimal(numStr).add(BigDecimal.ONE));
    }
}
