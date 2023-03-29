package com.xfei.mailgun.integration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.xfei.mailgun.constants.IntegrationTestConstants;
import com.xfei.mailgun.constants.TestConstants;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.xfei.mailgun.api.v3.MailgunMessagesApi;
import com.xfei.mailgun.client.MailgunClient;
import com.xfei.mailgun.model.message.MailgunMimeMessage;
import com.xfei.mailgun.model.message.Message;
import com.xfei.mailgun.model.message.MessageResponse;
import com.xfei.util.ObjectMapperUtil;
import feign.Request;
import feign.Response;
import feign.form.FormData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MailgunMessagesIntegrationTest {

//    private static final String AWESOMENESS_TXT_PATH = "src/test/resources/mailgun_awesomeness.txt";
//    private static final String HELLO_WORLD_TXT_PATH = "src/test/resources/hello_world.txt";
//    private static final String TEST_IMAGES_JPEG_PATH = "src/test/resources/test_images.jpeg";
//    private static final String MAILGUN_LOGO_PNG_PATH = "src/test/resources/mailgun_logo.png";
//
//    private static MailgunMessagesApi mailgunMessagesApi;
//
//    @BeforeAll
//    static void beforeAll() {
////        mailgunMessagesApi = MailgunClient.config(IntegrationTestConstants.PRIVATE_API_KEY)
////                .createApi(MailgunMessagesApi.class);
//    }
//
//    @Test
//    void message_MinimumFields_Test() {
//        Message message = Message.builder()
//                .from(IntegrationTestConstants.EMAIL_FROM)
//                .to(IntegrationTestConstants.EMAIL_TO)
//                .subject("Minimum fields example")
//                .text(TestConstants.TEST_EMAIL_TEXT)
//                .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertNotNull(result.getId());
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//    }
//
//    @Test
//    void message_MinimumFields_FeignResponse_Test() throws IOException {
//        Message message = Message.builder()
//                .from(IntegrationTestConstants.EMAIL_FROM)
//                .to(IntegrationTestConstants.EMAIL_TO)
//                .subject(TestConstants.TEST_EMAIL_SUBJECT)
//                .text(TestConstants.TEST_EMAIL_TEXT)
//                .build();
//
//        Response feignResponse = mailgunMessagesApi.sendMessageFeignResponse(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(200, feignResponse.status());
//        assertEquals("OK", feignResponse.reason());
//        Request request = feignResponse.request();
//        assertEquals(Request.HttpMethod.POST, request.httpMethod());
//        assertNotNull(feignResponse.body());
//        MessageResponse messageResponse = ObjectMapperUtil.decode(feignResponse, MessageResponse.class);
//        assertNotNull(messageResponse.getId());
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, messageResponse.getMessage());
//    }
//
//    @Test
//    void message_MinimumFields_FeignResponse_JsonNode_Test() throws IOException {
//        Message message = Message.builder()
//            .from(IntegrationTestConstants.EMAIL_FROM)
//            .to(IntegrationTestConstants.EMAIL_TO)
//            .subject(TestConstants.TEST_EMAIL_SUBJECT)
//            .text(TestConstants.TEST_EMAIL_TEXT)
//            .build();
//
//        Response feignResponse = mailgunMessagesApi.sendMessageFeignResponse(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(200, feignResponse.status());
//        assertEquals("OK", feignResponse.reason());
//        Request request = feignResponse.request();
//        assertEquals(Request.HttpMethod.POST, request.httpMethod());
//        assertNotNull(feignResponse.body());
//        JsonNode jsonNode = ObjectMapperUtil.decode(feignResponse, JsonNode.class);
//        assertEquals(2, jsonNode.size());
//        assertNotNull(jsonNode.get("id"));
//        Assertions.assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, jsonNode.get("message").asText());
//    }
//
//    @Test
//    void message_Template_Test() {
//        Map<String, Object> mailgunVariables = new LinkedHashMap<>();
//        mailgunVariables.put("name", TestConstants.TEST_USER_NAME);
//
//        Message message = Message.builder()
//                .from(IntegrationTestConstants.EMAIL_FROM)
//                .to(IntegrationTestConstants.EMAIL_TO)
//                .subject(TestConstants.TEST_EMAIL_SUBJECT)
//                .template(IntegrationTestConstants.TEMPLATE_NAME)
//                .templateVersion(IntegrationTestConstants.TEMPLATE_VERSION_TAG_1)
//                .renderTemplate(true)
//                .mailgunVariables(mailgunVariables)
//                .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
////        Template:
////        "Hey, {{name}}!"
////        Result:
////        Hey, Zarathustra!
//    }
//
//    @Test
//    void message_Batch_Sending_Template_Test() {
//        Map<String, Object> recipientVariables = new HashMap<>();
//
//        Map<String, Object> mVars = new HashMap<>();
//        mVars.put("name", "Alice");
//        mVars.put("id", 1);
//        recipientVariables.put(IntegrationTestConstants.EMAIL_TO, mVars);
//
//        Map<String, Object> bobVars = new HashMap<>();
//        bobVars.put("name", "Bob");
//        bobVars.put("id", 2);
//        recipientVariables.put(IntegrationTestConstants.EMAIL_FROM, bobVars);
//
//        Message message = Message.builder()
//            .from(IntegrationTestConstants.EMAIL_FROM)
//            .to(Arrays.asList(IntegrationTestConstants.EMAIL_TO, IntegrationTestConstants.EMAIL_FROM))
//            .subject("Hey %recipient.name%")
//            .text("If you wish to unsubscribe, click <https://mailgun.com/unsubscribe/%recipient.id%>")
//            .recipientVariables(recipientVariables)
//            .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//        //        EMAIL_TO recipient result:
//        //        subject: Hey Alice
//        //        text: If you wish to unsubscribe, click <https://mailgun.com/unsubscribe/1>
//
//        //        EMAIL_FROM recipient result:
//        //        subject: Hey Bob
//        //        text: If you wish to unsubscribe, click <https://mailgun.com/unsubscribe/2>
//    }
//
//    @Test
//    void message_HTML_Test() {
//        Message message = Message.builder()
//                .from(IntegrationTestConstants.EMAIL_FROM)
//                .to(IntegrationTestConstants.EMAIL_TO)
//                .subject("HTML example.")
//                .html("<html>\n" +
//                        "<body>\n" +
//                        "\t<h1>Sending HTML emails with Mailgun</h1>\n" +
//                        "\t<p style=\"color:blue; font-size:30px;\">Hello world</p>\n" +
//                        "\t<p style=\"font-size:30px;\">More examples can be found <a href=\"https://documentation.mailgun.com/en/latest/api-sending.html#examples\">here</a></p>\n" +
//                        "</body>\n" +
//                        "</html>")
//                .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//    }
//
//    @Test
//    void message_Attachment_Test() {
//        File helloWorld = new File(HELLO_WORLD_TXT_PATH);
//        File mailgunAwesomeness = new File(AWESOMENESS_TXT_PATH);
//        File testImages = new File(TEST_IMAGES_JPEG_PATH);
//        File mailgunLogo = new File(MAILGUN_LOGO_PNG_PATH);
//
//        Message message = Message.builder()
//                .from(IntegrationTestConstants.EMAIL_FROM)
//                .to(IntegrationTestConstants.EMAIL_TO)
//                .subject("Attachments example.")
//                .text(TestConstants.TEST_EMAIL_TEXT)
//                .attachment(helloWorld)
//                .attachment(testImages)
//                .attachment(Arrays.asList(mailgunAwesomeness, mailgunLogo))
//                .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//    }
//
//    @Test
//    void message_Attachment_FormData_Test() throws IOException {
//        FormData formData = getFormData("text/plain", "mailgun_awesomeness.txt", AWESOMENESS_TXT_PATH);
//
//        Message message = Message.builder()
//            .from(IntegrationTestConstants.EMAIL_FROM)
//            .to(IntegrationTestConstants.EMAIL_TO)
//            .subject("FormData attachment example. use my mult 1")
//            .text(TestConstants.TEST_EMAIL_TEXT)
//            .formData(formData)
//            .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//    }
//
//    @Test
//    void message_Attachments_FormData_Test() throws IOException {
//        FormData mailgunTextFormData = getFormData("text/plain", "mailgun_awesomeness.txt", AWESOMENESS_TXT_PATH);
//        FormData helloWorldTextFormData = getFormData("text/plain", "hello_world.txt", HELLO_WORLD_TXT_PATH);
//        FormData jpegFormData = getFormData("image/jpeg", "test_images.jpeg", TEST_IMAGES_JPEG_PATH);
//        FormData pngFormData = getFormData("image/png", "mailgun_logo.png", MAILGUN_LOGO_PNG_PATH);
//
//        Message message = Message.builder()
//            .from(IntegrationTestConstants.EMAIL_FROM)
//            .to(IntegrationTestConstants.EMAIL_TO)
//            .subject("FormData attachment example. use my mult Nov 7, 7")
//            .text(TestConstants.TEST_EMAIL_TEXT)
//            .formData(mailgunTextFormData)
//            .formData(Arrays.asList(helloWorldTextFormData, jpegFormData, pngFormData))
//            .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//    }
//
//    @Test
//    void message_Inline_Simple_Test() {
//        File mailgunLogo = new File(MAILGUN_LOGO_PNG_PATH);
//
//        Message message = Message.builder()
//                .from(IntegrationTestConstants.EMAIL_FROM)
//                .to(IntegrationTestConstants.EMAIL_TO)
//                .subject("Inline simple example.")
//                .text(TestConstants.TEST_EMAIL_TEXT)
//                .inline(mailgunLogo)
//                .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//    }
//
//    @Test
//    void message_Inline_Multiple_Files_Test() {
//        File mailgunLogo = new File(MAILGUN_LOGO_PNG_PATH);
//        File testImages = new File(TEST_IMAGES_JPEG_PATH);
//
//        Message message = Message.builder()
//                .from(IntegrationTestConstants.EMAIL_FROM)
//                .to(IntegrationTestConstants.EMAIL_TO)
//                .subject("Inline multiple files example.")
//                .html("Text above images." +
//                        "<div><img height=200 id=\"1\" src=\"cid:mailgun_logo.png\"/></div>" +
//                        "Text between images." +
//                        "<div><img id=\"2\" src=\"cid:test_images.jpeg\"/></div>" +
//                        "Text below images.")
//                .inline(mailgunLogo)
//                .inline(testImages)
//                .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//    }
//
//    @Test
//    void message_DeliveryTime_Test() {
//        Message message = Message.builder()
//                .from(IntegrationTestConstants.EMAIL_FROM)
//                .to(IntegrationTestConstants.EMAIL_TO)
//                .subject("Delivery time example.")
//                .text(TestConstants.TEST_EMAIL_TEXT)
////                .deliveryTime(ZonedDateTime.now().plusMinutes(2L)) // Two minutes delay.
//                .deliveryTime(ZonedDateTime.now().plusSeconds(20L)) // Two minutes delay.
//                .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//    }
//
//    @Test
//    void message_ReplyTo_Test() {
//        Message message = Message.builder()
//                .from(IntegrationTestConstants.EMAIL_FROM)
//                .to(IntegrationTestConstants.EMAIL_TO)
//                .subject("Reply-to example.")
//                .text(TestConstants.TEST_EMAIL_TEXT)
//                .replyTo(TestConstants.TEST_EMAIL_1)
//                .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//    }
//
//    @Test
//    void message_MailingList_Test() {
//        Message message = Message.builder()
//                .from(IntegrationTestConstants.EMAIL_FROM)
//                .to(IntegrationTestConstants.MAILING_LIST_ADDRESS)
//                .subject("Mailing List example")
//                .text(TestConstants.TEST_EMAIL_TEXT)
//                .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMessage(IntegrationTestConstants.MAIN_DOMAIN, message);
//
//        assertNotNull(result.getId());
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//    }
//
//    @Test
//    void sendMIMEMessage_Test() {
//        File mimeMessage = new File("src/test/resources/mime-message.txt");
//
//        MailgunMimeMessage mailgunMimeMessage = MailgunMimeMessage.builder()
//            .to(IntegrationTestConstants.EMAIL_TO)
//            .message(mimeMessage)
//            .build();
//
//        MessageResponse result = mailgunMessagesApi.sendMIMEMessage(IntegrationTestConstants.MAIN_DOMAIN, mailgunMimeMessage);
//
//        assertNotNull(result.getId());
//        assertEquals(TestConstants.EMAIL_RESPONSE_MESSAGE, result.getMessage());
//    }
//
//    private FormData getFormData(String contentType, String fileName, String path) throws IOException {
//        //        Emulate InputStream
//        InputStream inputStream = new FileInputStream(path);
//        byte[] bytes = IOUtils.toByteArray(inputStream);
//        return new FormData(contentType, fileName, bytes);
//    }

}
