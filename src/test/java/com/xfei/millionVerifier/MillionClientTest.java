package com.xfei.millionVerifier;

import com.xfei.millionverifier.api.MillionVerifierApi;
import com.xfei.millionverifier.client.MillionVerifierClient;
import com.xfei.millionverifier.model.MillionSingleResponse;
import com.xfei.millionverifier.model.MillionUploadRequest;
import com.xfei.millionverifier.model.MillionUploadResponse;
import com.xfei.util.ClientUtil;
import com.xfei.util.Constants;
import feign.Response;
import feign.form.FormData;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * descThisFile
 *
 * @author yzq
 * @date 2023/03/24 20:45
 */
class MillionClientTest {
    private MillionVerifierApi millionVerifierApi;

    private final String apiKey = "xxxx";

    @BeforeEach
    void setUp() {
        //批量校验
        millionVerifierApi = MillionVerifierClient.config()
                .createApiWithAbsoluteUrl(MillionVerifierApi.class);
        //单个校验
//        millionVerifierApi = MillionVerifierClient.config(Constants.MILLION_VERIFIER_DEFAULT_BASE_URL_US_REGION_SINGLE)
//                .createApiWithAbsoluteUrl(MillionVerifierApi.class);
    }

    @Test
    void uploadVerifyEmailAddressTest() throws IOException {
        FormData formData = ClientUtil.getFormData("text/plain", "email.txt", "/Users/xxxxx/Downloads/email.txt");
        MillionUploadRequest request = MillionUploadRequest.builder()
                .formData(formData)
                .build();

        MillionUploadResponse response = millionVerifierApi.uploadFile(apiKey, request);

        System.out.println(response.toString());
    }

    @Test
    void getFileInfoTest() {
        MillionUploadResponse response = millionVerifierApi.getFileInfo(apiKey,24152750L);
        System.out.println(response.toString());
    }

    @Test
    void downloadFileInfoTest() throws IOException {
        Response response = millionVerifierApi.downloadFileInfo(apiKey,24152750L);

        InputStream is = response.body().asInputStream();
        byte[] bytes = IOUtils.toByteArray(is);

        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
//        CSVParser csvParser = new CSVParserBuilder().build();
//        CSVReader reader = new CSVReaderBuilder(isr).withCSVParser(csvParser).build();
//        List<String[]> csvList = reader.readAll();

        System.out.println(new String(bytes));
    }

    @Test
    void verifySingleEmailAddressTest() {
        millionVerifierApi = MillionVerifierClient.config(Constants.MILLION_VERIFIER_DEFAULT_BASE_URL_US_REGION_SINGLE)
                .createApiWithAbsoluteUrl(MillionVerifierApi.class);
        MillionSingleResponse response = millionVerifierApi.singleVerify(apiKey,"123123123@qq.com");

        System.out.println(response.toString());
    }
}
