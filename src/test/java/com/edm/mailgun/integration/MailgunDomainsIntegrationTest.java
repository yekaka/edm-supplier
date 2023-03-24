package com.edm.mailgun.integration;

import com.edm.mailgun.constants.IntegrationTestConstants;
import com.edm.mailgun.api.MailgunApi;
import com.edm.mailgun.api.v3.MailgunDomainsApi;
import com.edm.mailgun.client.MailgunClient;
import com.edm.mailgun.enums.DomainState;
import com.edm.mailgun.enums.SpamAction;
import com.edm.mailgun.enums.WebScheme;
import com.edm.mailgun.enums.YesNo;
import com.edm.mailgun.enums.YesNoHtml;
import com.edm.mailgun.model.ResponseWithMessage;
import com.edm.mailgun.model.domains.Domain;
import com.edm.mailgun.model.domains.DomainClickTrackingStatus;
import com.edm.mailgun.model.domains.DomainConnection;
import com.edm.mailgun.model.domains.DomainConnectionRequest;
import com.edm.mailgun.model.domains.DomainConnectionResponse;
import com.edm.mailgun.model.domains.DomainCredentials;
import com.edm.mailgun.model.domains.DomainListResponse;
import com.edm.mailgun.model.domains.DomainRequest;
import com.edm.mailgun.model.domains.DomainResponse;
import com.edm.mailgun.model.domains.DomainTracking;
import com.edm.mailgun.model.domains.DomainTrackingResponse;
import com.edm.mailgun.model.domains.DomainTrackingStatus;
import com.edm.mailgun.model.domains.DomainTrackingUnsubscribeStatus;
import com.edm.mailgun.model.domains.DomainUnsubscribeConnectionSettingsRequest;
import com.edm.mailgun.model.domains.DomainsParametersFilter;
import com.edm.mailgun.model.domains.ReceivingDnsRecords;
import com.edm.mailgun.model.domains.SendingDnsRecord;
import com.edm.mailgun.model.domains.SingleDomainResponse;
import com.edm.mailgun.model.domains.UpdateDomainClickTrackingSettingsResponse;
import com.edm.mailgun.model.domains.UpdateDomainConnectionResponse;
import com.edm.mailgun.model.domains.UpdateDomainOpenTrackingSettingsResponse;
import com.edm.mailgun.model.domains.UpdateDomainUnsubscribeTrackingSettingsResponse;
import com.edm.mailgun.util.ObjectMapperUtil;
import feign.Request;
import feign.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.edm.mailgun.util.Constants.DEFAULT_BASE_URL_US_REGION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Use these tests as examples for a real implementation.")
class MailgunDomainsIntegrationTest {

    private static MailgunDomainsApi mailgunDomainsApi;

    @BeforeAll
    static void beforeAll() {
        mailgunDomainsApi = MailgunClient.config(IntegrationTestConstants.PRIVATE_API_KEY)
                .createApi(MailgunDomainsApi.class);
    }

    @Test
    void getAllDomainsSuccessTest() {
        DomainListResponse result = mailgunDomainsApi.getDomainsList();

        List<Domain> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        Domain domain = items.get(0);
        assertNotNull(result.getTotalCount());
        assertNotNull(domain.getId());
        assertNotNull(domain.getIsDisabled());
        assertNotNull(domain.getName());
        assertNotNull(domain.getRequireTls());
        assertNotNull(domain.getSkipVerification());
        assertNotNull(domain.getSmtpLogin());
        assertNotNull(domain.getSpamAction());
        assertNotNull(domain.getState());
        assertNotNull(domain.getType());
        assertNotNull(domain.getWebPrefix());
        assertNotNull(domain.getWebScheme());
        assertNotNull(domain.getWildcard());
        assertNotNull(domain.getCreatedAt());
    }

    @Test
    void getAllDomainsLimitSuccessTest() {
        DomainsParametersFilter limit = DomainsParametersFilter.builder()
                .limit(1)
                .skip(1)
                .state(DomainState.ACTIVE)
                .build();

        DomainListResponse result = mailgunDomainsApi.getDomainsList(limit);

        List<Domain> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        assertEquals(1, items.size());
        assertNotNull(result.getTotalCount());
        assertTrue(result.getTotalCount() > 1);
    }

    @Test
    void getAllDomainsResponseSuccessTest() throws IOException {
        Response feignResponse = mailgunDomainsApi.getDomainsListFeignResponse();

        assertEquals(200, feignResponse.status());
        assertEquals("OK", feignResponse.reason());
        Request request = feignResponse.request();
        assertEquals(Request.HttpMethod.GET, request.httpMethod());
        assertEquals(DEFAULT_BASE_URL_US_REGION + MailgunApi.getApiVersion().getValue() + "/domains", request.url());
        assertNotNull(feignResponse.body());
        DomainListResponse domainListResponse = ObjectMapperUtil.decode(feignResponse, DomainListResponse.class);
        assertTrue(CollectionUtils.isNotEmpty(domainListResponse.getItems()));
        assertNotNull(domainListResponse.getTotalCount());
    }

    @Test
    void getSingleDomainSuccessTest() {
        SingleDomainResponse result = mailgunDomainsApi.getSingleDomain(IntegrationTestConstants.MAIN_DOMAIN);

        assertNotNull(result);
        assertNotNull(result.getDomain());
        List<ReceivingDnsRecords> receivingDnsRecords = result.getReceivingDnsRecords();
        assertTrue(CollectionUtils.isNotEmpty(receivingDnsRecords));
        List<SendingDnsRecord> sendingDnsRecords = result.getSendingDnsRecords();
        assertTrue(CollectionUtils.isNotEmpty(sendingDnsRecords));
        ReceivingDnsRecords receivingDnsRecord = receivingDnsRecords.get(0);
        assertNotNull(receivingDnsRecord.getCached());
        assertNotNull(receivingDnsRecord.getPriority());
        assertNotNull(receivingDnsRecord.getRecordType());
        assertNotNull(receivingDnsRecord.getValid());
        assertNotNull(receivingDnsRecord.getValue());
        SendingDnsRecord sendingDnsRecord = sendingDnsRecords.get(0);
        assertNotNull(sendingDnsRecord.getCached());
        assertNotNull(sendingDnsRecord.getName());
        assertNotNull(sendingDnsRecord.getRecordType());
        assertNotNull(sendingDnsRecord.getValid());
        assertNotNull(sendingDnsRecord.getValue());
    }

    @Test
    void verifyDomainSuccessTest() {
        DomainResponse result = mailgunDomainsApi.verifyDomain(IntegrationTestConstants.MAIN_DOMAIN);

        assertNotNull(result);
        assertNotNull(result.getDomain());
        assertTrue(StringUtils.isNotEmpty(result.getMessage()));
        assertEquals("Domain DNS records have been updated", result.getMessage());
        assertTrue(CollectionUtils.isNotEmpty(result.getReceivingDnsRecords()));
        assertTrue(CollectionUtils.isNotEmpty(result.getSendingDnsRecords()));
    }

    @Test
    void createNewDomainSuccessTest() {
        DomainRequest request = DomainRequest.builder()
                .name(IntegrationTestConstants.DOMAIN_NAME)
                .spamAction(SpamAction.BLOCK)
                .wildcard(true)
                .forceDkimAuthority(false)
                .dkimKeySize(1024)
                .ips(Arrays.asList(IntegrationTestConstants.IP_1, IntegrationTestConstants.IP_2))
                .webScheme(WebScheme.HTTPS)
                .build();

        DomainResponse result = mailgunDomainsApi.createNewDomain(request);

        assertNotNull(result);
        assertEquals("Domain has been created", result.getMessage());
        Domain domain = result.getDomain();
        assertNotNull(domain);
        assertEquals(IntegrationTestConstants.DOMAIN_NAME, domain.getName());
        assertEquals(WebScheme.HTTPS, domain.getWebScheme());
        assertEquals(SpamAction.BLOCK, domain.getSpamAction());
        assertEquals(true, domain.getWildcard());
        assertTrue(CollectionUtils.isNotEmpty(result.getReceivingDnsRecords()));
        assertTrue(CollectionUtils.isNotEmpty(result.getSendingDnsRecords()));
    }

    @Test
    void deleteDomainSuccessTest() {
        ResponseWithMessage result = mailgunDomainsApi.deleteDomain(IntegrationTestConstants.DOMAIN_NAME);

        assertNotNull(result);
        assertEquals("Domain will be deleted on the background", result.getMessage());
    }

    @Test
    void createNewCredentialsSuccessTest() {
        String password = RandomStringUtils.randomAlphanumeric(15);
        DomainCredentials domainCredentials = DomainCredentials.builder()
                .login(IntegrationTestConstants.TEST_LOGIN)
                .password(password)
                .build();

        ResponseWithMessage result = mailgunDomainsApi.createNewCredentials(IntegrationTestConstants.DOMAIN_NAME, domainCredentials);

        assertNotNull(result);
        assertEquals("Created 1 credentials pair(s)", result.getMessage());
    }

    @Test
    void updateCredentialsSuccessTest() {
        String password = RandomStringUtils.randomAlphanumeric(15);

        ResponseWithMessage result = mailgunDomainsApi.updateCredentials(IntegrationTestConstants.DOMAIN_NAME, IntegrationTestConstants.TEST_LOGIN, password);

        assertNotNull(result);
        assertEquals("Password changed", result.getMessage());
    }

    @Test
    void deleteCredentialsSuccessTest() {
        ResponseWithMessage result = mailgunDomainsApi.deleteCredentials(IntegrationTestConstants.DOMAIN_NAME, IntegrationTestConstants.TEST_LOGIN);

        assertNotNull(result);
        assertEquals("Credentials have been deleted", result.getMessage());
    }

    @Test
    void getDomainConnectionSettingsSuccessTest() {
        DomainConnectionResponse result = mailgunDomainsApi.getDomainConnectionSettings(IntegrationTestConstants.DOMAIN_NAME);

        assertNotNull(result);
        DomainConnection connection = result.getConnection();
        assertNotNull(connection);
        assertNotNull(connection.getRequireTls());
        assertNotNull(connection.getSkipVerification());
    }

    @Test
    void updateDomainConnectionSettingsSuccessTest() {
        DomainConnectionRequest domainConnection = DomainConnectionRequest.builder()
                .requireTls(false)
                .skipVerification(false)
                .build();

        UpdateDomainConnectionResponse result = mailgunDomainsApi.updateDomainConnectionSettings(IntegrationTestConstants.DOMAIN_NAME, domainConnection);

        assertNotNull(result);
        assertTrue(StringUtils.isNotEmpty(result.getMessage()));
        assertNotNull(result.getRequireTls());
        assertNotNull(result.getSkipVerification());
    }

    @Test
    void getDomainTrackingSettingsSuccessTest() {
        DomainTrackingResponse result = mailgunDomainsApi.getDomainTrackingSettings(IntegrationTestConstants.DOMAIN_NAME);

        assertNotNull(result);
        DomainTracking tracking = result.getTracking();
        assertNotNull(tracking);
        DomainTrackingStatus click = tracking.getClick();
        assertNotNull(click);
        assertNotNull(click.getActive());
        DomainTrackingStatus open = tracking.getOpen();
        assertNotNull(open);
        assertNotNull(open.getActive());
        DomainTrackingUnsubscribeStatus unsubscribe = tracking.getUnsubscribe();
        assertNotNull(unsubscribe);
        assertNotNull(unsubscribe.getActive());
    }

    @Test
    void updateDomainConnectionSettingsForOpensSuccessTest() {
        UpdateDomainOpenTrackingSettingsResponse result = mailgunDomainsApi.updateDomainOpenTrackingSettings(IntegrationTestConstants.DOMAIN_NAME, YesNo.NO);

        assertNotNull(result);
        assertTrue(StringUtils.isNotEmpty(result.getMessage()));
        DomainTrackingStatus domainTrackingStatus = result.getOpen();
        assertNotNull(domainTrackingStatus);
        assertNotNull(domainTrackingStatus.getActive());
    }

    @Test
    void updateDomainClickTrackingSettingsSuccessTest() {
        UpdateDomainClickTrackingSettingsResponse result = mailgunDomainsApi.updateDomainClickTrackingSettings(IntegrationTestConstants.DOMAIN_NAME, YesNoHtml.HTML_ONLY);

        assertNotNull(result);
        assertTrue(StringUtils.isNotEmpty(result.getMessage()));
        DomainClickTrackingStatus domainClickTrackingStatus = result.getClick();
        assertNotNull(domainClickTrackingStatus);
        assertNotNull(domainClickTrackingStatus.getActive());
    }

    @Test
    void updateDomainUnsubscribeConnectionSettingsSuccessTest() {
        DomainUnsubscribeConnectionSettingsRequest request = DomainUnsubscribeConnectionSettingsRequest.builder()
                .active(false)
                .htmlFooter("\n<br>\n<p><a href=\\\"%unsubscribe_url%\\\">unsubscribe java</a></p>\n")
                .textFooter("\n\nTo unsubscribe from java click: <%unsubscribe_url%>\n\n")
                .build();

        UpdateDomainUnsubscribeTrackingSettingsResponse result = mailgunDomainsApi.updateDomainUnsubscribeConnectionSettings(IntegrationTestConstants.DOMAIN_NAME, request);

        assertNotNull(result);
        assertTrue(StringUtils.isNotEmpty(result.getMessage()));
        DomainTrackingUnsubscribeStatus unsubscribe = result.getUnsubscribe();
        assertNotNull(unsubscribe);
        assertNotNull(unsubscribe.getActive());
    }

}
