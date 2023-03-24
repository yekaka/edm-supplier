package com.xfei.mailgun.model.mailing.lists;

import com.xfei.mailgun.constants.TestConstants;
import com.xfei.mailgun.enums.YesNo;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MailingListMemberUpdateRequestTest {

    @Test
    void builderSuccessTest() {
        Map<String, Object> vars = new HashMap<>();
        vars.put(TestConstants.GENDER, TestConstants.MALE);
        vars.put(TestConstants.AGE, TestConstants.AGE_VALUE);

        MailingListMemberUpdateRequest request = MailingListMemberUpdateRequest.builder()
                .address(TestConstants.TEST_EMAIL_1)
                .name(TestConstants.TEST_USER_NAME)
                .vars(vars)
                .subscribed(true)
                .build();

        assertEquals(TestConstants.TEST_EMAIL_1, request.getAddress());
        assertEquals(TestConstants.TEST_USER_NAME, request.getName());
        assertTrue(request.getVars().entrySet().containsAll(vars.entrySet()));
        assertEquals(YesNo.YES.getValue(), request.getSubscribed());
    }

}
