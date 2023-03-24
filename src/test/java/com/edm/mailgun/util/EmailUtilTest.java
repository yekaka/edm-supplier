package com.edm.mailgun.util;

import com.edm.mailgun.constants.TestConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailUtilTest {

    @Test
    void nameWithEmailTest() {
        String result = EmailUtil.nameWithEmail(TestConstants.TEST_USER_NAME, TestConstants.TEST_EMAIL_1);

        assertEquals("Zarathustra <some-fake-address-01@example.com>", result);
    }
}
