package com.xfei.mailgun.api;

import com.xfei.mailgun.constants.TestConstants;
import com.xfei.mailgun.utils.MessageUtils;
import com.xfei.mailgun.utils.TestHeadersUtils;
import com.xfei.mailgun.api.v3.MailgunMessagesApi;
import com.xfei.mailgun.client.MailgunClient;
import com.xfei.mailgun.model.message.Message;
import com.xfei.mailgun.model.message.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MailgunApiMessagesTest extends WireMockBaseTest {

    private MailgunMessagesApi mailgunMessagesApi;

    @BeforeEach
    void setUp() {
        mailgunMessagesApi = MailgunClient.config(wireMockServer.baseUrl(), TestConstants.TEST_API_KEY)
                .createApi(MailgunMessagesApi.class);
    }

    @Test
    void sendMessageWithDomainSuccessTest() {
        stubFor(post(urlEqualTo("/" + MailgunApi.getApiVersion().getValue() + "/" + TestConstants.TEST_DOMAIN + "/messages"))
                .withHeader("Authorization", equalTo(TestHeadersUtils.getExpectedAuthHeader()))
                .withHeader("Content-Type",
                        containing("multipart/form-data"))
                .withHeader("Accept",
                        equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(MessageUtils.getMessageResponseJsonString())));

        Message message = Message.builder()
                .from(TestConstants.TEST_EMAIL_1)
                .to(TestConstants.TEST_EMAIL_2)
                .subject(TestConstants.TEST_EMAIL_SUBJECT)
                .text(TestConstants.TEST_EMAIL_TEXT)
                .build();

        MessageResponse result = mailgunMessagesApi.sendMessage(TestConstants.TEST_DOMAIN, message);

        assertEquals(TestConstants.EMAIL_RESPONSE_ID, result.getId());
        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
    }

}
