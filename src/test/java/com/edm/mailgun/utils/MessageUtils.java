package com.edm.mailgun.utils;

import com.edm.mailgun.constants.TestConstants;
import com.edm.mailgun.model.message.MessageResponse;
import com.edm.mailgun.util.StringUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageUtils {

    public MessageResponse getMessageResponse() {
        return MessageResponse.builder()
                .id(TestConstants.EMAIL_RESPONSE_ID)
                .message(TestConstants.EMAIL_RESPONSE_MESSAGE)
                .build();
    }

    public String getMessageResponseJsonString() {
        return StringUtil.asJsonString(getMessageResponse());
    }

}
