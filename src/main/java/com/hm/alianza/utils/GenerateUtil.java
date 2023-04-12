package com.hm.alianza.utils;

import com.hm.alianza.common.Constants;

public class GenerateUtil {

    private GenerateUtil() {}

    public static String generateSharedId(String businessId) {
        return businessId.split(Constants.EMPTY)[Constants.ZERO].substring(Constants.ZERO,Constants.ONE)
                .concat(businessId.split(Constants.EMPTY)[Constants.ONE]);
    }

}
