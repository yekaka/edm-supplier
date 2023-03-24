package com.xfei.mailgun.api;

import com.xfei.mailgun.enums.ApiVersion;

public interface MailgunApi {

    static ApiVersion getApiVersion() {
        return ApiVersion.V_3;
    }

}
