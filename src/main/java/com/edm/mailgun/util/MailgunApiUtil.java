package com.edm.mailgun.util;

import java.lang.reflect.Method;

import com.edm.mailgun.enums.ApiVersion;
import org.apache.commons.lang3.StringUtils;

import com.edm.mailgun.api.MailgunApi;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MailgunApiUtil {

    @SneakyThrows
    public String getFullUrl(Class<? extends MailgunApi> apiType, String baseUrl) {
        ApiVersion apiVersion;
        try {
            Method declaredMethod = apiType.getDeclaredMethod("getApiVersion");
            apiVersion = (ApiVersion) declaredMethod.invoke(null);
        } catch (Exception e) {
            apiVersion = MailgunApi.getApiVersion();
        }

        return getFullUrl(baseUrl, apiVersion.getValue());
    }

    private String getFullUrl(String baseUrl, String apiVersion) {
        return (StringUtils.endsWith(baseUrl, "/") ? baseUrl + apiVersion : baseUrl + "/" + apiVersion);
    }

}
