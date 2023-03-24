package com.xfei.mailgun.integration;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import com.xfei.mailgun.constants.IntegrationTestConstants;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.xfei.mailgun.api.v4.MailgunEmailVerificationApi;
import com.xfei.mailgun.client.MailgunClient;
import com.xfei.mailgun.model.verification.AddressValidationResponse;
import com.xfei.mailgun.model.verification.BulkVerificationCreatingResponse;
import com.xfei.mailgun.model.verification.BulkVerificationDownloadUrl;
import com.xfei.mailgun.model.verification.BulkVerificationJobListResponse;
import com.xfei.mailgun.model.verification.BulkVerificationJobStatusResponse;
import com.xfei.mailgun.model.verification.BulkVerificationPreviewListResponse;
import com.xfei.mailgun.model.verification.BulkVerificationPreviewResponse;
import com.xfei.mailgun.model.verification.BulkVerificationPreviewStatusResponse;
import com.xfei.mailgun.model.verification.BulkVerificationStatusRequest;
import com.xfei.mailgun.model.verification.BulkVerificationStatusSummary;
import com.xfei.mailgun.model.verification.BulkVerificationStatusSummaryResult;
import com.xfei.mailgun.model.verification.BulkVerificationStatusSummaryRisk;
import feign.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Use these tests as examples for a real implementation.")
class MailgunEmailVerificationIntegrationTest {

    private static final String TEST_LIST_NAME = "test_list_1";
    private static MailgunEmailVerificationApi mailgunEmailVerificationApi;

    @BeforeAll
    static void beforeAll() {
        mailgunEmailVerificationApi = MailgunClient.config(IntegrationTestConstants.PRIVATE_API_KEY)
                .createApi(MailgunEmailVerificationApi.class);
    }

    @Test
    void validateAddressSuccessTest() {
        AddressValidationResponse result = mailgunEmailVerificationApi.validateAddress(IntegrationTestConstants.EMAIL_TO);

        assertTrue(StringUtils.isNotBlank(result.getAddress()));
        assertNotNull(result.getIsDisposableAddress());
        assertNotNull(result.getIsDisposableAddress());
        assertNotNull(result.getReason());
        assertTrue(StringUtils.isNotBlank(result.getResult()));
        assertTrue(StringUtils.isNotBlank(result.getRisk()));
    }

    @Test
    void validateAddressProviderLookupSuccessTest() {
        AddressValidationResponse result = mailgunEmailVerificationApi.validateAddress(IntegrationTestConstants.EMAIL_TO, true);

        assertTrue(StringUtils.isNotBlank(result.getAddress()));
        assertNotNull(result.getIsDisposableAddress());
        assertNotNull(result.getIsDisposableAddress());
        assertNotNull(result.getReason());
        assertTrue(StringUtils.isNotBlank(result.getResult()));
        assertTrue(StringUtils.isNotBlank(result.getRisk()));
    }

    @Test
    void validateAddressPostSuccessTest() {
        AddressValidationResponse result = mailgunEmailVerificationApi.validateAddressPostRequest(IntegrationTestConstants.EMAIL_TO);

        assertTrue(StringUtils.isNotBlank(result.getAddress()));
        assertNotNull(result.getIsDisposableAddress());
        assertNotNull(result.getIsDisposableAddress());
        assertNotNull(result.getReason());
        assertTrue(StringUtils.isNotBlank(result.getResult()));
        assertTrue(StringUtils.isNotBlank(result.getRisk()));
    }

    @Test
    void validateAddressPostProviderLookupSuccessTest() {
        AddressValidationResponse result = mailgunEmailVerificationApi.validateAddressPostRequest(IntegrationTestConstants.EMAIL_TO, true);

        assertTrue(StringUtils.isNotBlank(result.getAddress()));
        assertNotNull(result.getIsDisposableAddress());
        assertNotNull(result.getIsDisposableAddress());
        assertNotNull(result.getReason());
        assertTrue(StringUtils.isNotBlank(result.getResult()));
        assertTrue(StringUtils.isNotBlank(result.getRisk()));
    }

    @Test
    void createBulkVerificationJobSuccessTest() throws IOException {
        List<String> dataLines = Arrays.asList("email", IntegrationTestConstants.EMAIL_TO, IntegrationTestConstants.EMAIL_FROM);
        File csvOutputFile = File.createTempFile("emails", ".csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.forEach(pw::println);
        }
        assertTrue(csvOutputFile.exists());
        csvOutputFile.deleteOnExit();

        BulkVerificationStatusRequest request = BulkVerificationStatusRequest.builder()
            .file(csvOutputFile)
            .build();

        BulkVerificationCreatingResponse result = mailgunEmailVerificationApi
            .createBulkVerificationJob(TEST_LIST_NAME, request);

        assertNotNull(result.getId());
        assertNotNull(result.getMessage());
        assertEquals("The validation job was submitted.", result.getMessage());
    }

    @Test
    void getBulkVerificationJobListSuccessTest() {
        BulkVerificationJobListResponse result = mailgunEmailVerificationApi.getBulkVerificationJobList();

        assertNotNull(result.getJobs());
        assertNotNull(result.getTotal());
        assertNotNull(result.getPaging());
    }

    @Test
    void getBulkVerificationJobListLimitSuccessTest() {
        BulkVerificationJobListResponse result = mailgunEmailVerificationApi.getBulkVerificationJobList(1);

        assertNotNull(result.getJobs());
        assertEquals(1, result.getJobs().size());
        assertNotNull(result.getTotal());
        assertNotNull(result.getPaging());
    }

    @Test
    void getBulkVerificationJobStatusSuccessTest() {
        BulkVerificationJobStatusResponse result = mailgunEmailVerificationApi.getBulkVerificationJobStatus(TEST_LIST_NAME);

        assertTrue(StringUtils.isNotBlank(result.getId()));
        assertNotNull(result.getQuantity());
        assertNotNull(result.getRecordsProcessed());
        assertTrue(StringUtils.isNotBlank(result.getStatus()));
        BulkVerificationStatusSummary summary = result.getSummary();
        assertNotNull(summary);
        BulkVerificationStatusSummaryResult summaryResult = summary.getResult();
        assertNotNull(summaryResult.getCatchAll());
        assertNotNull(summaryResult.getDeliverable());
        assertNotNull(summaryResult.getDoNotSend());
        assertNotNull(summaryResult.getUndeliverable());
        assertNotNull(summaryResult.getUnknown());
        BulkVerificationStatusSummaryRisk risk = summary.getRisk();
        assertNotNull(risk);
        assertNotNull(risk.getHigh());
        assertNotNull(risk.getLow());
        assertNotNull(risk.getMedium());
        assertNotNull(risk.getUnknown());
        assertNotNull(result.getCreatedAt());
        BulkVerificationDownloadUrl downloadUrl = result.getDownloadUrl();
        assertNotNull(downloadUrl);
        assertTrue(StringUtils.isNotBlank(downloadUrl.getCsv()));
        assertTrue(StringUtils.isNotBlank(downloadUrl.getJson()));
    }

    @Test
    void cancelBulkVerificationJobSuccessTest() {
        String result = mailgunEmailVerificationApi.cancelBulkVerificationJob(TEST_LIST_NAME);

        assertEquals("Validation job canceled.", result);
    }

    @Test
    void getBulkVerificationPreviewListSuccessTest() {
        BulkVerificationPreviewListResponse result = mailgunEmailVerificationApi.getBulkVerificationPreviewList();

        assertNotNull(result.getPreviews());
    }

    @Test
    void getBulkVerificationPreviewStatusSuccessTest() {
        BulkVerificationPreviewResponse result = mailgunEmailVerificationApi.getBulkVerificationPreviewStatus(TEST_LIST_NAME);

        BulkVerificationPreviewStatusResponse preview = result.getPreview();
        assertNotNull(preview);
        assertTrue(StringUtils.isNotBlank(preview.getId()));
        assertNotNull(preview.getQuantity());
        assertTrue(StringUtils.isNotBlank(preview.getStatus()));
        assertNotNull(preview.getValid());
        assertNotNull(preview.getCreatedAt());
    }

    @Test
    void cancelBulkVerificationPreviewSuccessTest() {
        Response result = mailgunEmailVerificationApi.deleteBulkVerificationPreview(TEST_LIST_NAME);

        assertEquals(204, result.status());
    }

}
