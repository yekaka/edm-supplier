package com.xfei.mailgun.integration;

import com.xfei.mailgun.api.v3.MailgunEventsApi;
import com.xfei.mailgun.client.MailgunClient;
import com.xfei.mailgun.constants.IntegrationTestConstants;
import com.xfei.mailgun.constants.TestConstants;
import com.xfei.mailgun.enums.EventType;
import com.xfei.mailgun.enums.Severity;
import com.xfei.mailgun.model.events.EventItem;
import com.xfei.mailgun.model.events.EventMessage;
import com.xfei.mailgun.model.events.EventsQueryOptions;
import com.xfei.mailgun.model.events.EventsResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Use these tests as examples for a real implementation.")
class MailgunEventsIntegrationTest {

    private static MailgunEventsApi mailgunEventsApi;

    @BeforeAll
    static void beforeAll() {
        mailgunEventsApi = MailgunClient.config(IntegrationTestConstants.PRIVATE_API_KEY)
                .createApi(MailgunEventsApi.class);
    }

    @Test
    void getAllEventsSuccessTest() {
        EventsResponse result = mailgunEventsApi.getAllEvents(IntegrationTestConstants.MAIN_DOMAIN);

        assertNotNull(result);
        assertTrue(CollectionUtils.isNotEmpty(result.getItems()));
        assertNotNull(result.getPaging());
    }

    @Test
    void getEventsQueryOptionsEventSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .event(EventType.DELIVERED)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        items.forEach(event -> assertEquals(EventType.DELIVERED.getValue(), event.getEvent()));
    }

    @Test
    void getEventsFieldFilterExpressionANDSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .eventFieldFilterExpression(EventType.DELIVERED.getValue() + " OR " + EventType.ACCEPTED.getValue())
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        Set<String> events = items.stream()
                .map(EventItem::getEvent)
                .collect(Collectors.toSet());
        assertEquals(2, events.size());
        assertTrue(events.containsAll(Arrays.asList(EventType.DELIVERED.getValue(), EventType.ACCEPTED.getValue())));
    }

    @Test
    void getEventsFieldFilterExpressionNOTSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .eventFieldFilterExpression("NOT delivered")
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        items.forEach(event -> assertNotEquals(EventType.DELIVERED.getValue(), event.getEvent()));
    }

    @Test
    void getEventsQueryOptionsTimeRangeSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .begin(TestConstants.ZONED_DATE_TIME_2022_16_02)
                .end(TestConstants.EPOCH_TIME_FEB_22_2022)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        EventItem event = items.get(0);
        assertNotNull(event.getId());
        assertTrue(event.getTimestamp().isAfter(TestConstants.ZONED_DATE_TIME_2022_16_02));
        assertTrue(event.getTimestamp().toEpochSecond() < TestConstants.EPOCH_TIME_FEB_22_2022);
    }

    @Test
    void getEventsQueryOptionsAscendingSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .begin(TestConstants.ZONED_DATE_TIME_2022_16_02)
                .ascending(true)
                .limit(1)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertEquals(1, items.size());
        EventItem event = items.get(0);
        assertNotNull(event.getId());
        assertTrue(event.getTimestamp().isAfter(TestConstants.ZONED_DATE_TIME_2022_16_02));
    }

    @Test
    void getEventsQueryOptionsSeveritySuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .severity(Severity.TEMPORARY)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        items.forEach(event -> Assertions.assertEquals(Severity.TEMPORARY, event.getSeverity()));
    }

    @Test
    void getEventsQueryOptionsFromSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .from(IntegrationTestConstants.EMAIL_FROM)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        items.forEach(event -> Assertions.assertEquals(IntegrationTestConstants.EMAIL_FROM, event.getMessage().getHeaders().getFrom()));
    }

    @Test
    void getEventsQueryOptionsRecipientSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .recipient(IntegrationTestConstants.EMAIL_TO)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        items.forEach(event -> Assertions.assertEquals(IntegrationTestConstants.EMAIL_TO, event.getRecipient()));
    }

    @Test
    void getEventsQueryOptionsRecipientFieldFilterNOTExpressionSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .recipient("NOT " + IntegrationTestConstants.EMAIL_TO)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        items.forEach(event -> Assertions.assertNotEquals(IntegrationTestConstants.EMAIL_TO, event.getRecipient()));
    }

    @Test
    void getEventsQueryOptionsRecipientFieldFilterExpressionSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .recipient(IntegrationTestConstants.EMAIL_TO + " OR " + IntegrationTestConstants.EMAIL_FROM)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        Set<String> recipients = items.stream()
                .map(EventItem::getRecipient)
                .collect(Collectors.toSet());
        assertEquals(2, recipients.size());
        assertTrue(recipients.containsAll(Arrays.asList(IntegrationTestConstants.EMAIL_TO, IntegrationTestConstants.EMAIL_FROM)));
    }

    @Test
    void getEventsQueryOptionsRecipientsSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .recipients(IntegrationTestConstants.EMAIL_TO)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        items.forEach(event -> assertTrue(event.getMessage().getRecipients().contains(IntegrationTestConstants.EMAIL_TO)));
    }

    @Test
    void getEventsQueryOptionsRecipientsFieldFilterExpressionSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .recipients("NOT " + IntegrationTestConstants.EMAIL_TO)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        Set<String> recipients = items.stream()
                .map(EventItem::getMessage)
                .filter(Objects::nonNull)
                .map(EventMessage::getRecipients)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
        assertTrue(CollectionUtils.isNotEmpty(recipients));
        assertFalse(recipients.contains(IntegrationTestConstants.EMAIL_TO));
    }

    @Test
    void getEventsToSuccessTest() {
        EventsQueryOptions eventsQuery = EventsQueryOptions.builder()
                .limit(1)
                .build();

        EventsResponse firstPageResult = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQuery);

        List<EventItem> items = firstPageResult.getItems();
        assertEquals(1, items.size());
        String to = items.get(0).getMessage().getHeaders().getTo();

        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .to(to)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> events = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(events));
        events.forEach(event -> assertEquals(to, event.getMessage().getHeaders().getTo()));
    }

    @Test
    void getEventsMessageIdSuccessTest() {
        EventsQueryOptions eventsQuery = EventsQueryOptions.builder()
                .limit(1)
                .build();

        EventsResponse firstPageResult = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQuery);

        List<EventItem> items = firstPageResult.getItems();
        assertEquals(1, items.size());
        String messageId = items.get(0).getMessage().getHeaders().getMessageId();

        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .messageId(messageId)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> events = result.getItems();
        assertEquals(messageId, events.get(0).getMessage().getHeaders().getMessageId());
    }

    @Test
    void getEventsSubjectSuccessTest() {
        EventsQueryOptions eventsQuery = EventsQueryOptions.builder()
                .limit(1)
                .build();

        EventsResponse firstPageResult = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQuery);

        List<EventItem> items = firstPageResult.getItems();
        assertEquals(1, items.size());
        String subject = items.get(0).getMessage().getHeaders().getSubject();

        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .subject(subject)
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> events = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(events));
        events.forEach(event -> assertEquals(subject, event.getMessage().getHeaders().getSubject()));
    }

    @Test
    void getEventsQueryOptionsSizeFieldFilterExpressionSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .size(">344 <379")
                .build();

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        assertNotNull(result);
        List<EventItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        items.forEach(event -> {
            Integer size = event.getMessage().getSize();
            assertTrue(size > 344);
            assertTrue(size < 379);
        });
    }

    @Test
    void getEventsQueryOptionsTagsFieldFilterExpressionSuccessTest() {
        EventsQueryOptions eventsQuery = EventsQueryOptions.builder()
                .limit(300)
                .build();

        EventsResponse resultAll = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQuery);

        Optional<String> tagOptional = resultAll.getItems().stream()
                .map(EventItem::getTags)
                .filter(CollectionUtils::isNotEmpty)
                .findFirst()
                .map(tags -> tags.get(0));

        if (tagOptional.isPresent()) {
            String tag = tagOptional.get();
            EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                    .tags(tag)
                    .build();

            EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

            assertNotNull(result);
            List<EventItem> items = result.getItems();
            assertTrue(CollectionUtils.isNotEmpty(items));
            items.forEach(event -> assertTrue(event.getTags().contains(tag)));
        }
    }

    @Test
    void getEventsNextPageSuccessTest() {
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .limit(1)
                .build();

        EventsResponse firstPageResult = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, eventsQueryOptions);

        String nextPageId = firstPageResult.getPaging().getNext().split("/events/")[1];

        EventsResponse result = mailgunEventsApi.getEvents(IntegrationTestConstants.MAIN_DOMAIN, nextPageId);

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
    }


}
