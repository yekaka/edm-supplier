package com.xfei.mailgun.utils;

import com.xfei.mailgun.constants.TestConstants;
import com.xfei.mailgun.model.message.MessageResponse;
import com.xfei.util.StringUtil;
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
