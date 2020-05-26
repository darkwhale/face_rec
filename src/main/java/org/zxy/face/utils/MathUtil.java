package org.zxy.face.utils;

public class MathUtil {

    public static String add(String numStr){
        if (numStr.equals("")) {
            return "1";
        }
        return String.valueOf(Integer.parseInt(numStr) + 1);
    }
}
