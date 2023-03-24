package com.edm.mailgun.model.suppression.unsubscribe;

import com.edm.mailgun.constants.TestConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnsubscribeSingleItemRequestTest {

    @Test
    void builderSuccessTest() {
        UnsubscribeSingleItemRequest request = UnsubscribeSingleItemRequest.builder()
                .address(TestConstants.TEST_EMAIL_1)
                .tag(TestConstants.TEST_TAG_1)
                .createdAt(TestConstants.ZONED_DATE_TIME_2018_2_3_GMT)
                .build();

        assertEquals(TestConstants.TEST_EMAIL_1, request.getAddress());
        assertEquals(TestConstants.TEST_TAG_1, request.getTag());
        assertEquals(TestConstants.ZONED_DATE_TIME_2018_2_03_GMT_STRING, request.getCreatedAt());
    }

}
