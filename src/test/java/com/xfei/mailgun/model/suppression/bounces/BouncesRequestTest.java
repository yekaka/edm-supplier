package com.xfei.mailgun.model.suppression.bounces;

import com.xfei.mailgun.constants.TestConstants;
import org.junit.jupiter.api.Test;

import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BouncesRequestTest {

    @Test
    void builderSuccessTest() {
        BouncesRequest request = BouncesRequest.builder()
                .address(TestConstants.TEST_EMAIL_1)
                .code("550")
                .error(ERROR_MESSAGE)
                .createdAt(TestConstants.ZONED_DATE_TIME_2018_2_3_GMT)
                .build();

        assertEquals(TestConstants.TEST_EMAIL_1, request.getAddress());
        assertEquals("550", request.getCode());
        assertEquals(ERROR_MESSAGE, request.getError());
        assertEquals(TestConstants.ZONED_DATE_TIME_2018_2_3_GMT_STRING, request.getCreatedAt());
    }

}
