package com.xfei.mailgun.model.message;

import com.xfei.mailgun.constants.TestConstants;
import com.xfei.mailgun.enums.YesNo;
import com.xfei.mailgun.enums.YesNoHtml;
import com.xfei.mailgun.util.EmailUtil;
import feign.form.FormData;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.xfei.mailgun.util.Constants.FIELD_CANNOT_BE_NULL_OR_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MessageTest {

    @Test
    void messageMinimumFieldsSuccessTest() {
        Message result = Message.builder()
                .from(TestConstants.TEST_EMAIL_1)
                .to(TestConstants.TEST_EMAIL_2)
                .build();

        assertNotNull(result);
        assertEquals(TestConstants.TEST_EMAIL_1, result.getFrom());
        assertEquals(1, result.getTo().size());
        assertNull(result.getCc());
        assertNull(result.getBcc());
        assertNull(result.getSubject());
        assertNull(result.getText());
        assertNull(result.getAttachment());
        assertNull(result.getInline());
        assertNull(result.getTemplateVersion());
        assertNull(result.getRenderTemplate());
        assertNull(result.getTag());
        assertNull(result.getDkim());
        assertNull(result.getDeliveryTime());
        assertNull(result.getTestMode());
        assertNull(result.getTracking());
        assertNull(result.getTrackingClicks());
        assertNull(result.getTrackingOpens());
        assertNull(result.getRequireTls());
        assertNull(result.getSkipVerification());
        assertNull(result.getReplyTo());
        assertNull(result.getRecipientVariables());
    }

    @Test
    void messageAllFieldsSuccessTest() throws IOException {
        File file1 = getTempFile("temp.1");
        File file2 = getTempFile("temp.2");
        File file3 = getTempFile("temp.3");
        File file4 = getTempFile("temp.4");
        Map<String, Object> recipientVariables = new HashMap<>();
        Map<String, String> aliceVars = new HashMap<>();
        aliceVars.put("Alice", "1");
        Map<String, String> bobVars = new HashMap<>();
        bobVars.put("Bob", "2");
        recipientVariables.put("firstEmail", aliceVars);
        recipientVariables.put("secondEmail", bobVars);

        Message result = Message.builder()
                .from(TestConstants.TEST_EMAIL_1)
                .to(TestConstants.TEST_EMAIL_2)
                .to(EmailUtil.nameWithEmail(TestConstants.TEST_USER_NAME, TestConstants.TEST_EMAIL_3))
                .to(Arrays.asList(TestConstants.TEST_EMAIL_4, TestConstants.TEST_EMAIL_5))
                .cc(TestConstants.TEST_EMAIL_2)
                .cc(TestConstants.TEST_EMAIL_3)
                .cc(Arrays.asList(TestConstants.TEST_EMAIL_4, TestConstants.TEST_EMAIL_5))
                .bcc(Arrays.asList(TestConstants.TEST_EMAIL_4, TestConstants.TEST_EMAIL_5))
                .bcc(TestConstants.TEST_EMAIL_2)
                .bcc(TestConstants.TEST_EMAIL_3)
                .subject(TestConstants.TEST_EMAIL_SUBJECT)
                .text(TestConstants.TEST_EMAIL_TEXT)
                .attachment(file1)
                .attachment(file2)
                .attachment(Arrays.asList(file3, file4))
                .inline(file1)
                .inline(file2)
                .inline(Arrays.asList(file3, file4))
                .templateVersion(TestConstants.TEMPLATE_VERSION)
                .renderTemplate(true)
                .tag(TestConstants.TEST_TAG_1)
                .tag(TestConstants.TEST_TAG_2)
                .tag(Arrays.asList(TestConstants.TEST_TAG_3, TestConstants.TEST_TAG_4))
                .dkim(true)
                .deliveryTime(TestConstants.ZONED_DATE_TIME_2018_2_3_GMT)
                .testMode(true)
                .tracking(true)
                .trackingClicks(YesNoHtml.YES)
                .trackingOpens(true)
                .requireTls(true)
                .skipVerification(true)
                .replyTo(TestConstants.TEST_EMAIL_2)
                .recipientVariables(recipientVariables)
                .build();

        assertNotNull(result);
        assertEquals(TestConstants.TEST_EMAIL_1, result.getFrom());
        assertEquals(4, result.getTo().size());
        assertEquals(TestConstants.TEST_EMAIL_SUBJECT, result.getSubject());
        assertEquals(TestConstants.TEST_EMAIL_TEXT, result.getText());
        assertEquals(4, result.getAttachment().size());
        assertEquals(4, result.getInline().size());
        assertEquals(TestConstants.TEMPLATE_VERSION, result.getTemplateVersion());
        assertEquals(YesNo.YES.getValue(), result.getRenderTemplate());
        assertEquals(4, result.getTag().size());
        assertEquals(YesNo.YES.getValue(), result.getDkim());
        assertEquals(TestConstants.ZONED_DATE_TIME_2018_2_3_GMT_STRING, result.getDeliveryTime());
        assertEquals(YesNo.YES.getValue(), result.getTestMode());
        assertEquals(YesNo.YES.getValue(), result.getTracking());
        assertEquals(YesNoHtml.YES.getValue(), result.getTrackingClicks());
        assertEquals(YesNo.YES.getValue(), result.getTrackingOpens());
        assertEquals(YesNo.YES.getValue(), result.getRequireTls());
        assertEquals(YesNo.YES.getValue(), result.getSkipVerification());
        assertEquals(TestConstants.TEST_EMAIL_2, result.getReplyTo());
        assertEquals("{\"firstEmail\":{\"Alice\":\"1\"},\"secondEmail\":{\"Bob\":\"2\"}}", result.getRecipientVariables());
    }

    @Test
    void messageAllFieldsSetFalseSuccessTest() {
        Message result = Message.builder()
                .from(TestConstants.TEST_EMAIL_1)
                .to(TestConstants.TEST_EMAIL_2)
                .html(TestConstants.TEST_EMAIL_HTML)
                .renderTemplate(false)
                .dkim(false)
                .testMode(false)
                .tracking(false)
                .trackingClicks(YesNoHtml.HTML_ONLY)
                .trackingOpens(false)
                .requireTls(false)
                .skipVerification(false)
                .build();

        assertNotNull(result);

        assertEquals(TestConstants.TEST_EMAIL_HTML, result.getHtml());
        assertNull(result.getRenderTemplate());
        assertEquals(YesNo.NO.getValue(), result.getDkim());
        assertNull(result.getTestMode());
        assertEquals(YesNo.NO.getValue(), result.getTracking());
        assertEquals(YesNoHtml.HTML_ONLY.getValue(), result.getTrackingClicks());
        assertEquals(YesNo.NO.getValue(), result.getTrackingOpens());
        assertEquals(YesNo.NO.getValue(), result.getRequireTls());
        assertEquals(YesNo.NO.getValue(), result.getSkipVerification());

    }

    @Test
    void messageFieldFromNullExceptionTest() {
        Message.MessageBuilder messageBuilder = Message.builder();

        Exception exception = assertThrows(IllegalArgumentException.class, messageBuilder::build);

        assertEquals(String.format(FIELD_CANNOT_BE_NULL_OR_EMPTY, "from"), exception.getMessage());
    }

    @Test
    void messageFieldFromEmptyExceptionTest() {
        Message.MessageBuilder messageBuilder = Message.builder()
                .from(StringUtils.EMPTY);

        Exception exception = assertThrows(IllegalArgumentException.class, messageBuilder::build);

        assertEquals(String.format(FIELD_CANNOT_BE_NULL_OR_EMPTY, "from"), exception.getMessage());
    }

    @Test
    void messageFieldFromSpaceExceptionTest() {
        Message.MessageBuilder messageBuilder = Message.builder()
                .from(StringUtils.SPACE);

        Exception exception = assertThrows(IllegalArgumentException.class, messageBuilder::build);

        assertEquals(String.format(FIELD_CANNOT_BE_NULL_OR_EMPTY, "from"), exception.getMessage());
    }

    @Test
    void messageFieldToNullExceptionTest() {
        Message.MessageBuilder messageBuilder = Message.builder()
                .from(TestConstants.TEST_EMAIL_1);

        Exception exception = assertThrows(IllegalArgumentException.class, messageBuilder::build);

        assertEquals(String.format(FIELD_CANNOT_BE_NULL_OR_EMPTY, "to"), exception.getMessage());
    }

    @Test
    void messageFieldToEmptyListExceptionTest() {
        Message.MessageBuilder messageBuilder = Message.builder()
                .from(TestConstants.TEST_EMAIL_1)
                .to(Collections.emptyList());

        Exception exception = assertThrows(IllegalArgumentException.class, messageBuilder::build);

        assertEquals(String.format(FIELD_CANNOT_BE_NULL_OR_EMPTY, "to"), exception.getMessage());
    }

    @Test
    void messageFieldToEmptyExceptionTest() {
        Message.MessageBuilder messageBuilder = Message.builder()
                .from(TestConstants.TEST_EMAIL_1)
                .to(Arrays.asList(StringUtils.SPACE, StringUtils.EMPTY));

        Exception exception = assertThrows(IllegalArgumentException.class, messageBuilder::build);

        assertEquals(String.format(FIELD_CANNOT_BE_NULL_OR_EMPTY, "to"), exception.getMessage());
    }


    @Test
    void messageAttachmentAndFromDataTogetherExceptionTest() throws IOException {
        File file = getTempFile("temp.1");
        InputStream inputStream = new FileInputStream(getTempFile("temp.2"));
        byte[] txtBytes = IOUtils.toByteArray(inputStream);
        FormData formData = new FormData("text/plain", "temp.txt", txtBytes);

        Message.MessageBuilder messageBuilder = Message.builder()
            .from(TestConstants.TEST_EMAIL_1)
            .to(TestConstants.TEST_EMAIL_2)
            .attachment(file)
            .formData(formData);

        Exception exception = assertThrows(IllegalArgumentException.class, messageBuilder::build);

        assertEquals("You cannot use 'attachment' and 'formData' together", exception.getMessage());
    }

    private File getTempFile(String prefix) throws IOException {
        File file = File.createTempFile(prefix, ".tmp", new File("src/test/java/com/mailgun/utils"));
        file.deleteOnExit();
        return file;
    }

}
