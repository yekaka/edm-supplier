package com.edm.mailgun.integration;

import com.edm.mailgun.constants.IntegrationTestConstants;
import com.edm.mailgun.constants.TestConstants;
import com.edm.mailgun.api.v3.MailgunMailingListApi;
import com.edm.mailgun.client.MailgunClient;
import com.edm.mailgun.enums.AccessLevel;
import com.edm.mailgun.enums.ReplyPreference;
import com.edm.mailgun.model.mailing.lists.AddMailingListMembersRequest;
import com.edm.mailgun.model.mailing.lists.DeleteMailingListResponse;
import com.edm.mailgun.model.mailing.lists.MailingListData;
import com.edm.mailgun.model.mailing.lists.MailingListDataResponse;
import com.edm.mailgun.model.mailing.lists.MailingListMember;
import com.edm.mailgun.model.mailing.lists.MailingListMemberResponse;
import com.edm.mailgun.model.mailing.lists.MailingListMemberUpdateRequest;
import com.edm.mailgun.model.mailing.lists.MailingListMembersRequest;
import com.edm.mailgun.model.mailing.lists.MailingListMembersResponse;
import com.edm.mailgun.model.mailing.lists.MailingListNewMemberRequest;
import com.edm.mailgun.model.mailing.lists.MailingListRequest;
import com.edm.mailgun.model.mailing.lists.MailingListResponse;
import com.edm.mailgun.model.mailing.lists.MailingListVerificationResponse;
import com.edm.mailgun.model.mailing.lists.MailingListVerificationStatusResponse;
import com.edm.mailgun.model.mailing.lists.SingleMailingListResponse;
import com.edm.mailgun.model.mailing.lists.UpdateMailingListRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Use these tests as examples for a real implementation.")
class MailgunMailingListApiIntegrationTest {

    private static Map<String, Object> vars;

    private static final String MEMBER_1_EMAIL = IntegrationTestConstants.EMAIL_FROM;
    private static final String MEMBER_2_EMAIL = IntegrationTestConstants.EMAIL_TO;

    private static MailgunMailingListApi mailgunMailingListApi;

    @BeforeAll
    static void beforeAll() {
        mailgunMailingListApi = MailgunClient.config(IntegrationTestConstants.PRIVATE_API_KEY)
                .createApi(MailgunMailingListApi.class);

        vars = new HashMap<>();
        vars.put(TestConstants.GENDER, TestConstants.MALE);
        vars.put(TestConstants.AGE, TestConstants.AGE_VALUE);
    }

    @Test
    void getMailingListSuccessTest() {
        MailingListDataResponse result = mailgunMailingListApi.getMailingList();

        List<MailingListData> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        assertTrue(items.size() > 2);
        MailingListData mailingListData = items.get(0);
        assertNotNull(mailingListData.getAccessLevel());
        assertNotNull(mailingListData.getAddress());
        assertNotNull(mailingListData.getCreatedAt());
        assertNotNull(mailingListData.getDescription());
        assertNotNull(mailingListData.getMembersCount());
        assertNotNull(mailingListData.getName());
        assertNotNull(mailingListData.getReplyPreference());
        assertNotNull(result.getPaging());
    }

    @Test
    void getMailingListLimitSuccessTest() {
        MailingListDataResponse result = mailgunMailingListApi.getMailingList(2);

        List<MailingListData> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        assertEquals(2, items.size());
    }

    @Test
    void getMailingListByAddressSuccessTest() {
        MailingListDataResponse mailingList = mailgunMailingListApi.getMailingList(1);
        List<MailingListData> items = mailingList.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        String mailingListAddress = items.get(0).getAddress();

        SingleMailingListResponse result = mailgunMailingListApi.getMailingListByAddress(mailingListAddress);

        MailingListData mailingListData = result.getList();
        assertNotNull(mailingListData.getAccessLevel());
        assertNotNull(mailingListData.getAddress());
        assertNotNull(mailingListData.getCreatedAt());
        assertNotNull(mailingListData.getDescription());
        assertNotNull(mailingListData.getMembersCount());
        assertNotNull(mailingListData.getName());
        assertNotNull(mailingListData.getReplyPreference());
    }

    @Test
    void createMailingListSuccessTest() {
        String description = "Some description of test team";
        String mailingListName = "Test team 1";
        MailingListRequest mailingListRequest = MailingListRequest.builder()
                .address(IntegrationTestConstants.MAILING_LIST_ADDRESS)
                .name(mailingListName)
                .description(description)
                .accessLevel(AccessLevel.EVERYONE)
                .replyPreference(ReplyPreference.LIST)
                .build();

        MailingListResponse result = mailgunMailingListApi.createMailingList(mailingListRequest);

        assertEquals("Mailing list has been created", result.getMessage());
        MailingListData mailingListData = result.getList();
        assertEquals(AccessLevel.EVERYONE, mailingListData.getAccessLevel());
        assertEquals(IntegrationTestConstants.MAILING_LIST_ADDRESS, mailingListData.getAddress());
        assertNotNull(mailingListData.getCreatedAt());
        assertEquals(description, mailingListData.getDescription());
        assertEquals(0, mailingListData.getMembersCount());
        assertEquals(mailingListName, mailingListData.getName());
        assertEquals(ReplyPreference.LIST, mailingListData.getReplyPreference());
    }

    @Test
    void updateMailingListSuccessTest() {
        UpdateMailingListRequest request = UpdateMailingListRequest.builder()
                .address(TestConstants.TEST_EMAIL_1)
                .name("Updated Test team 1")
                .description("Some new description of test team 1")
                .accessLevel(AccessLevel.EVERYONE)
                .replyPreference(ReplyPreference.LIST)
                .build();

        MailingListResponse result = mailgunMailingListApi.updateMailingList(IntegrationTestConstants.MAILING_LIST_ADDRESS, request);

        assertEquals("Mailing list has been updated", result.getMessage());
        MailingListData mailingListData = result.getList();
        assertNotNull(mailingListData.getAccessLevel());
        assertNotNull(mailingListData.getAddress());
        assertNotNull(mailingListData.getCreatedAt());
        assertNotNull(mailingListData.getDescription());
        assertNotNull(mailingListData.getMembersCount());
        assertNotNull(mailingListData.getName());
        assertNotNull(mailingListData.getReplyPreference());
    }

    @Test
    void deleteMailingListSuccessTest() {
        DeleteMailingListResponse result = mailgunMailingListApi.deleteMailingList(IntegrationTestConstants.MAILING_LIST_ADDRESS);

        assertEquals("Mailing list has been removed", result.getMessage());
        assertEquals(IntegrationTestConstants.MAILING_LIST_ADDRESS, result.getAddress());
    }

    @Test
    void verifyMailingListMembersSuccessTest() {
        MailingListVerificationResponse result = mailgunMailingListApi.verifyMailingListMembers(IntegrationTestConstants.MAILING_LIST_ADDRESS);

        assertEquals(IntegrationTestConstants.MAILING_LIST_ADDRESS, result.getId());
        assertEquals("The validation job was submitted.", result.getMessage());
    }

    @Test
    void getMailingListVerificationJobStatusSuccessTest() {
        MailingListVerificationStatusResponse result = mailgunMailingListApi.getMailingListVerificationJobStatus(IntegrationTestConstants.MAILING_LIST_ADDRESS);

        assertEquals(IntegrationTestConstants.MAILING_LIST_ADDRESS, result.getId());
        assertNotNull(result.getStatus());
        assertNotNull(result.getQuantity());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void cancelActiveMailingListVerificationJobSuccessTest() {
        String result = mailgunMailingListApi.cancelActiveMailingListVerificationJob(IntegrationTestConstants.MAILING_LIST_ADDRESS);

        assertEquals("Validation job canceled.", result);
    }

    @Test
    void getMailingListMembersSuccessTest() {
        MailingListMembersResponse result = mailgunMailingListApi.getMailingListMembers(IntegrationTestConstants.MAILING_LIST_ADDRESS);

        List<MailingListMember> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        assertTrue(items.size() > 1);
        MailingListMember mailingListMember = items.get(0);
        assertNotNull(mailingListMember.getAddress());
        assertNotNull(mailingListMember.getName());
        assertNotNull(mailingListMember.getVars());
        assertNotNull(mailingListMember.getSubscribed());
        assertNotNull(result.getPaging());
    }

    @Test
    void getMailingListMembersLimitSuccessTest() {
        MailingListMembersRequest request = MailingListMembersRequest.builder()
                .limit(1)
                .build();

        MailingListMembersResponse result = mailgunMailingListApi.getMailingListMembers(IntegrationTestConstants.MAILING_LIST_ADDRESS, request);

        List<MailingListMember> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        assertEquals(1, items.size());
    }

    @Test
    void getMailingListMemberSuccessTest() {
        MailingListMemberResponse result = mailgunMailingListApi.getMailingListMember(IntegrationTestConstants.MAILING_LIST_ADDRESS, MEMBER_1_EMAIL);

        assertEquals("Mailing list member has been created", result.getMessage());
        MailingListMember mailingListMember = result.getMember();
        assertNotNull(mailingListMember.getAddress());
        assertNotNull(mailingListMember.getName());
        assertNotNull(mailingListMember.getVars());
        assertNotNull(mailingListMember.getSubscribed());
    }

    @Test
    void addMemberToMailingListSuccessTest() {
        MailingListNewMemberRequest request = MailingListNewMemberRequest.builder()
                .address(MEMBER_1_EMAIL)
                .name(TestConstants.TEST_USER_NAME)
                .vars(vars)
                .subscribed(true)
                .build();

        MailingListMemberResponse result = mailgunMailingListApi.addMemberToMailingList(IntegrationTestConstants.MAILING_LIST_ADDRESS, request);

        assertEquals("Mailing list member has been created", result.getMessage());
        MailingListMember mailingListMember = result.getMember();
        assertEquals(MEMBER_1_EMAIL, mailingListMember.getAddress());
        assertEquals(TestConstants.TEST_USER_NAME, mailingListMember.getName());
        assertTrue(mailingListMember.getVars().entrySet().containsAll(vars.entrySet()));
        assertEquals(true, mailingListMember.getSubscribed());
    }

    @Test
    void updateMailingListMemberSuccessTest() {
        String newName = "Updated name";

        Map<String, Object> newVarsMap;
        newVarsMap = new HashMap<>();
        newVarsMap.put(TestConstants.GENDER, TestConstants.MALE);
        newVarsMap.put(TestConstants.AGE, TestConstants.AGE_VALUE + 5);

        MailingListMemberUpdateRequest request = MailingListMemberUpdateRequest.builder()
                .name(newName)
                .vars(newVarsMap)
                .subscribed(true)
                .build();

        MailingListMemberResponse result = mailgunMailingListApi.updateMailingListMember(IntegrationTestConstants.MAILING_LIST_ADDRESS, MEMBER_1_EMAIL, request);

        assertEquals("Mailing list member has been updated", result.getMessage());
        MailingListMember mailingListMember = result.getMember();
        assertEquals(MEMBER_1_EMAIL, mailingListMember.getAddress());
        assertEquals(newName, mailingListMember.getName());
        assertTrue(mailingListMember.getVars().entrySet().containsAll(newVarsMap.entrySet()));
        assertEquals(true, mailingListMember.getSubscribed());
    }

    @Test
    void addMembersToMailingListSuccessTest() {
        MailingListMember mailingListMember_1 = MailingListMember.builder()
                .address(MEMBER_1_EMAIL)
                .name(TestConstants.TEST_USER_NAME)
                .vars(vars)
                .subscribed(true)
                .build();

        MailingListMember mailingListMember_2 = MailingListMember.builder()
                .address(MEMBER_2_EMAIL)
                .name(TestConstants.TEST_USER_NAME)
                .vars(vars)
                .subscribed(true)
                .build();

        AddMailingListMembersRequest request = AddMailingListMembersRequest.builder()
                .members(Arrays.asList(mailingListMember_1, mailingListMember_2))
                .upsert(true)
                .build();

        MailingListResponse result = mailgunMailingListApi.addMembersToMailingList(IntegrationTestConstants.MAILING_LIST_ADDRESS, request);

        assertEquals("Mailing list has been updated", result.getMessage());
        assertNotNull(result.getTaskId());
        MailingListData mailingListData = result.getList();
        assertNotNull(mailingListData.getAccessLevel());
        assertEquals(IntegrationTestConstants.MAILING_LIST_ADDRESS, mailingListData.getAddress());
        assertNotNull(mailingListData.getCreatedAt());
        assertNotNull(mailingListData.getDescription());
        assertNotNull(mailingListData.getMembersCount());
        assertNotNull(mailingListData.getName());
        assertNotNull(mailingListData.getReplyPreference());
    }

    @Test
    void deleteMemberFromMailingListSuccessTest() {
        MailingListMemberResponse result = mailgunMailingListApi.deleteMemberFromMailingList(IntegrationTestConstants.MAILING_LIST_ADDRESS, MEMBER_2_EMAIL);

        assertEquals("Mailing list member has been deleted", result.getMessage());
        MailingListMember mailingListMember = result.getMember();
        assertNotNull(mailingListMember.getAddress());
    }

}
