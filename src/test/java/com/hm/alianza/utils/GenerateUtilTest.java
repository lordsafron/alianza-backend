package com.hm.alianza.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateUtilTest {

    @Test
    void generateSharedId() {

        var businessId = "pedro perez";

        var result = GenerateUtil.generateSharedId(businessId);
        Assertions.assertEquals("pperez", result);

    }
}