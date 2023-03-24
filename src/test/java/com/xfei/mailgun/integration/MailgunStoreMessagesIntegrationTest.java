package com.xfei.mailgun.integration;

import java.io.IOException;
import java.util.stream.StreamSupport;

import com.xfei.mailgun.api.v3.MailgunEventsApi;
import com.xfei.mailgun.api.v3.MailgunStoreMessagesApi;
import com.xfei.mailgun.client.MailgunClient;
import com.xfei.mailgun.constants.IntegrationTestConstants;
import com.xfei.mailgun.constants.TestConstants;
import com.xfei.mailgun.model.events.EventsQueryOptions;
import com.xfei.mailgun.model.events.EventsResponse;
import com.xfei.mailgun.model.message.MessageResponse;
import com.xfei.mailgun.model.message.StoreMessageResponse;
import com.xfei.mailgun.util.ObjectMapperUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Use these tests as examples for a real implementation.")
class MailgunStoreMessagesIntegrationTest {

    private static String storedMessageUrl;

    @BeforeAll
    static void beforeAll() {
        MailgunEventsApi mailgunEventsApi = MailgunClient.config(IntegrationTestConstants.PRIVATE_API_KEY)
            .createApi(MailgunEventsApi.class);

        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
            .messageId("the id of the required message")
            .build();

        EventsResponse events = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        storedMessageUrl = events.getItems().get(0).getStorage().getUrl();
    }

    @Test
    void resendMessage_UnsupportedOperationException_Test() {
        assertThrows(UnsupportedOperationException.class, () ->
            MailgunClient.config(storedMessageUrl, IntegrationTestConstants.PRIVATE_API_KEY)
                .createApi(MailgunStoreMessagesApi.class)
        );
    }

    @Test
    void resendMessageTest() {
        MailgunStoreMessagesApi mailgunStoreMessagesApi = MailgunClient.config(storedMessageUrl, IntegrationTestConstants.PRIVATE_API_KEY)
            .createApiWithAbsoluteUrl(MailgunStoreMessagesApi.class);

        MessageResponse result = mailgunStoreMessagesApi.resendMessage(IntegrationTestConstants.EMAIL_TO);

        assertNotNull(result.getId());
        Assertions.assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
    }

    @Test
    void retrieveMessageTest() {
        MailgunStoreMessagesApi mailgunStoreMessagesApi = MailgunClient.config(storedMessageUrl, IntegrationTestConstants.PRIVATE_API_KEY)
            .createApiWithAbsoluteUrl(MailgunStoreMessagesApi.class);

        StoreMessageResponse result = mailgunStoreMessagesApi.retrieveMessage();

        assertTrue(StringUtils.isNotEmpty(result.getFrom()));
        assertTrue(StringUtils.isNotEmpty(result.getTo()));
    }

    @Test
    void retrieveMessage_FeignResponse_JsonNode_Test() throws IOException {
        MailgunStoreMessagesApi mailgunStoreMessagesApi = MailgunClient.config(storedMessageUrl, IntegrationTestConstants.PRIVATE_API_KEY)
            .createApiWithAbsoluteUrl(MailgunStoreMessagesApi.class);

        Response feignResponse = mailgunStoreMessagesApi.retrieveMessageFeignResponse();

        JsonNode jsonNode = ObjectMapperUtil.decode(feignResponse, JsonNode.class);
        assertTrue(jsonNode.size() > 10);
        assertNotNull(jsonNode.get("From"));
        assertNotNull(jsonNode.get("To"));
        assertNotNull(jsonNode.get("Message-Id"));
        JsonNode messageHeaders = jsonNode.get("message-headers");
        assertTrue(messageHeaders.isArray());
        Double mimeVersion = StreamSupport.stream(messageHeaders.spliterator(), false)
            .filter(node -> node.get(0).asText().equals("Mime-Version"))
            .map(node -> node.get(1))
            .map(JsonNode::asDouble)
            .findFirst()
            .orElse(null);
        assertNotNull(mimeVersion);
        assertTrue(mimeVersion >= 1.0);
    }

}