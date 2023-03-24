package com.xfei.mailgun.model.events;

import com.xfei.mailgun.constants.TestConstants;
import com.xfei.mailgun.enums.EventType;
import com.xfei.mailgun.enums.Severity;
import com.xfei.mailgun.enums.YesNo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventsQueryOptionsTest {

    @Test
    void builderTimeRangeZonedDateTimeSuccessTest() {
        EventsQueryOptions queryOptions = EventsQueryOptions.builder()
                .begin(TestConstants.ZONED_DATE_TIME_2022_16_02)
                .end(TestConstants.EPOCH_TIME_FEB_22_2022)
                .build();

        assertEquals(1645017900, queryOptions.getBegin());
        assertEquals(1645536360, queryOptions.getEnd());
    }

    @Test
    void builderTimeRangeLongSuccessTest() {
        EventsQueryOptions queryOptions = EventsQueryOptions.builder()
                .begin(1645017900L)
                .end(1645536360L)
                .build();

        assertEquals(1645017900, queryOptions.getBegin());
        assertEquals(1645536360, queryOptions.getEnd());
    }

    @Test
    void builderFilterOtherFieldsSuccessTest() {
        String sizeFilterExpression = ">10000 <20000";
        String attachment = "hello_world.txt";
        String messageId = "12345";
        EventsQueryOptions eventsQueryOptions = EventsQueryOptions.builder()
                .ascending(true)
                .limit(2)
                .event(EventType.DELIVERED)
                .attachment(attachment)
                .from(TestConstants.TEST_EMAIL_1)
                .messageId(messageId)
                .subject(TestConstants.TEST_EMAIL_SUBJECT)
                .to(TestConstants.TEST_EMAIL_2)
                .size(sizeFilterExpression)
                .recipient(TestConstants.TEST_EMAIL_3)
                .tags(TestConstants.TEST_TAG_1)
                .severity(Severity.TEMPORARY)
                .build();

        assertEquals(YesNo.YES.getValue(), eventsQueryOptions.getAscending());
        assertEquals(2, eventsQueryOptions.getLimit());
        assertEquals(EventType.DELIVERED.getValue(), eventsQueryOptions.getEvent());
        assertEquals(attachment, eventsQueryOptions.getAttachment());
        assertEquals(TestConstants.TEST_EMAIL_1, eventsQueryOptions.getFrom());
        assertEquals(messageId, eventsQueryOptions.getMessageId());
        assertEquals(TestConstants.TEST_EMAIL_SUBJECT, eventsQueryOptions.getSubject());
        assertEquals(TestConstants.TEST_EMAIL_2, eventsQueryOptions.getTo());
        assertEquals(sizeFilterExpression, eventsQueryOptions.getSize());
        assertEquals(TestConstants.TEST_EMAIL_3, eventsQueryOptions.getRecipient());
        assertEquals(TestConstants.TEST_TAG_1, eventsQueryOptions.getTags());
        assertEquals(Severity.TEMPORARY.getValue(), eventsQueryOptions.getSeverity());
    }

}
