package com.optimizely.helper;

public class Util {
    static boolean validateParam(String param) {
        if (param == null || param.isEmpty()) {
            return false;
        }

        return true;
    }
}
