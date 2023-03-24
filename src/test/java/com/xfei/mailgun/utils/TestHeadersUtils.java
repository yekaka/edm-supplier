package com.xfei.mailgun.utils;

import com.xfei.mailgun.constants.TestConstants;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Base64;

@UtilityClass
public class TestHeadersUtils {

    public String getExpectedAuthHeader() {
        String usernamePassword = "api:" + TestConstants.TEST_API_KEY;
        return "Basic " + Base64.encodeBase64String(usernamePassword.getBytes());
    }

}
