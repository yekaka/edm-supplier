package com.xfei.mailgun.model.suppression.complaints;

import com.xfei.mailgun.constants.TestConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComplaintsSingleItemRequestTest {

    @Test
    void builderSuccessTest() {
        ComplaintsSingleItemRequest request = ComplaintsSingleItemRequest.builder()
                .address(TestConstants.TEST_EMAIL_1)
                .createdAt(TestConstants.ZONED_DATE_TIME_2018_2_3_GMT)
                .build();

        assertEquals(TestConstants.TEST_EMAIL_1, request.getAddress());
        assertEquals(TestConstants.ZONED_DATE_TIME_2018_2_03_GMT_STRING, request.getCreatedAt());
    }

}
