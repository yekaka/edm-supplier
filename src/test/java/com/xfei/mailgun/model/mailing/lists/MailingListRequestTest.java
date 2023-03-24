package com.xfei.mailgun.model.mailing.lists;

import com.xfei.mailgun.constants.TestConstants;
import com.xfei.mailgun.enums.AccessLevel;
import com.xfei.mailgun.enums.ReplyPreference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MailingListRequestTest {

    @Test
    void builderSuccessTest() {
        String description = "Some description of test team";
        String mailingListName = "Test team 1";
        MailingListRequest request = MailingListRequest.builder()
                .address(TestConstants.TEST_EMAIL_1)
                .name(mailingListName)
                .description(description)
                .accessLevel(AccessLevel.EVERYONE)
                .replyPreference(ReplyPreference.LIST)
                .build();

        assertEquals(TestConstants.TEST_EMAIL_1, request.getAddress());
        assertEquals(mailingListName, request.getName());
        assertEquals(description, request.getDescription());
        assertEquals(AccessLevel.EVERYONE.getValue(), request.getAccessLevel());
        assertEquals(ReplyPreference.LIST.getValue(), request.getReplyPreference());
    }

}
