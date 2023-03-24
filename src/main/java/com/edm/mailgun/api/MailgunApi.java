package com.edm.mailgun.api;

import com.edm.mailgun.enums.ApiVersion;

public interface MailgunApi {

    static ApiVersion getApiVersion() {
        return ApiVersion.V_3;
    }

}
