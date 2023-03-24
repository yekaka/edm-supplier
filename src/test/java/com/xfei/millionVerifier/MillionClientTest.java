package com.xfei.millionVerifier;

import com.xfei.millionverifier.api.MillionVerifierApi;
import com.xfei.millionverifier.client.MillionVerifierClient;
import com.xfei.millionverifier.model.MillionSingleResponse;
import com.xfei.millionverifier.model.MillionUploadResponse;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * descThisFile
 *
 * @author yzq
 * @date 2023/03/24 20:45
 */
class MillionClientTest {
    private MillionVerifierApi millionVerifierApi;

    @BeforeEach
    void setUp() {
//        millionVerifierApi = MillionVerifierClient.config()
//                .createApiWithAbsoluteUrl(MillionVerifierApi.class);
        millionVerifierApi = MillionVerifierClient.config("https://api.millionverifier.com/api")
                .createApiWithAbsoluteUrl(MillionVerifierApi.class);
    }

    @Test
    void VerifyAnEmailAddressTest() {
        String apiKey = "DPKLX54foIX9pHQOOHI0rgTso";
        MillionSingleResponse response = millionVerifierApi.singleVerify(apiKey,"24148906@qq.com");
        System.out.println(response.toString());
    }
}
