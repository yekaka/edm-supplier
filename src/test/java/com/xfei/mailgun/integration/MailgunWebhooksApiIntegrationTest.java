package com.xfei.mailgun.integration;

import com.xfei.mailgun.api.v3.MailgunWebhooksApi;
import com.xfei.mailgun.client.MailgunClient;
import com.xfei.mailgun.constants.IntegrationTestConstants;
import com.xfei.mailgun.enums.WebhookName;
import com.xfei.mailgun.model.webhooks.Webhook;
import com.xfei.mailgun.model.webhooks.WebhookDetailsResult;
import com.xfei.mailgun.model.webhooks.WebhookListResult;
import com.xfei.mailgun.model.webhooks.WebhookRequest;
import com.xfei.mailgun.model.webhooks.WebhookResult;
import com.xfei.mailgun.model.webhooks.WebhookUpdateRequest;
import com.xfei.mailgun.model.webhooks.Webhooks;
import feign.FeignException;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Use these tests as examples for a real implementation.")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MailgunWebhooksApiIntegrationTest {

    private static MailgunWebhooksApi mailgunWebhooksApi;

    @BeforeAll
    static void beforeAll() {
        mailgunWebhooksApi = MailgunClient.config(IntegrationTestConstants.PRIVATE_API_KEY)
                .createApi(MailgunWebhooksApi.class);
    }

    @Order(1)
    @Test
    void createNewWebhookSuccessTest() {
        WebhookRequest request = WebhookRequest.builder()
                .webhookName(WebhookName.CLICKED)
                .url(IntegrationTestConstants.WEBHOOK_URL_1)
                .urls(Arrays.asList(IntegrationTestConstants.WEBHOOK_URL_2, IntegrationTestConstants.WEBHOOK_URL_3))
                .build();

        WebhookResult result = mailgunWebhooksApi.createNewWebhook(IntegrationTestConstants.MAIN_DOMAIN, request);

        assertEquals("Webhook has been created", result.getMessage());
        Webhook webhook = result.getWebhook();
        assertNotNull(webhook);
        assertTrue(CollectionUtils.isNotEmpty(webhook.getUrls()));
        assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(IntegrationTestConstants.WEBHOOK_URL_1, IntegrationTestConstants.WEBHOOK_URL_2, IntegrationTestConstants.WEBHOOK_URL_3), webhook.getUrls()));
    }

    @Order(2)
    @Test
    void getAllWebhooksSuccessTest() {
        WebhookListResult result = mailgunWebhooksApi.getAllWebhooks(IntegrationTestConstants.MAIN_DOMAIN);

        Webhooks webhooks = result.getWebhooks();
        assertNotNull(webhooks);
        Webhook clicked = webhooks.getClicked();
        assertNotNull(clicked);
        List<String> urls = clicked.getUrls();
        assertTrue(CollectionUtils.isNotEmpty(urls));
        assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(IntegrationTestConstants.WEBHOOK_URL_1, IntegrationTestConstants.WEBHOOK_URL_2, IntegrationTestConstants.WEBHOOK_URL_3), urls));
    }

    @Order(3)
    @Test
    void getWebhookDetailsSuccessTest() {
        WebhookDetailsResult result = mailgunWebhooksApi.getWebhookDetails(IntegrationTestConstants.MAIN_DOMAIN, WebhookName.CLICKED);

        Webhook webhook = result.getWebhook();
        assertNotNull(webhook);
        List<String> urls = webhook.getUrls();
        assertTrue(CollectionUtils.isNotEmpty(urls));
        assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(IntegrationTestConstants.WEBHOOK_URL_1, IntegrationTestConstants.WEBHOOK_URL_2, IntegrationTestConstants.WEBHOOK_URL_3), urls));
    }

    @Order(4)
    @Test
    void updateWebhookSuccessTest() {
        WebhookUpdateRequest request = WebhookUpdateRequest.builder()
                .url(IntegrationTestConstants.WEBHOOK_URL_4)
                .urls(Collections.singletonList(IntegrationTestConstants.WEBHOOK_URL_3))
                .build();

        WebhookResult result = mailgunWebhooksApi.updateWebhook(IntegrationTestConstants.MAIN_DOMAIN, WebhookName.CLICKED, request);

        assertEquals("Webhook has been updated", result.getMessage());
        Webhook webhook = result.getWebhook();
        assertNotNull(webhook);
        List<String> urls = webhook.getUrls();
        assertTrue(CollectionUtils.isNotEmpty(urls));
        assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(IntegrationTestConstants.WEBHOOK_URL_3, IntegrationTestConstants.WEBHOOK_URL_4), urls));
    }

    @Order(5)
    @Test
    void deleteWebhookSuccessTest() {
        WebhookResult result = mailgunWebhooksApi.deleteWebhook(IntegrationTestConstants.MAIN_DOMAIN, WebhookName.CLICKED);

        assertEquals("Webhook has been deleted", result.getMessage());
        Webhook webhook = result.getWebhook();
        assertNotNull(webhook);
        List<String> urls = webhook.getUrls();
        assertTrue(CollectionUtils.isNotEmpty(urls));
        assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(IntegrationTestConstants.WEBHOOK_URL_3, IntegrationTestConstants.WEBHOOK_URL_4), urls));

        FeignException exception = assertThrows(FeignException.class, () ->
                mailgunWebhooksApi.getWebhookDetails(IntegrationTestConstants.MAIN_DOMAIN, WebhookName.CLICKED)
        );

        assertTrue(exception.getMessage().contains("Webhook not found"));
        assertEquals(404, exception.status());
    }

}
