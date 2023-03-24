package com.xfei.mailgun.api.v3;

import com.xfei.mailgun.enums.YesNo;
import com.xfei.mailgun.enums.YesNoHtml;
import com.xfei.mailgun.expanders.EnumExpander;
import com.xfei.mailgun.model.ResponseWithMessage;
import com.xfei.mailgun.model.domains.DomainConnectionRequest;
import com.xfei.mailgun.model.domains.DomainConnectionResponse;
import com.xfei.mailgun.model.domains.DomainCredentials;
import com.xfei.mailgun.model.domains.DomainListResponse;
import com.xfei.mailgun.model.domains.DomainRequest;
import com.xfei.mailgun.model.domains.DomainResponse;
import com.xfei.mailgun.model.domains.DomainTrackingResponse;
import com.xfei.mailgun.model.domains.DomainUnsubscribeConnectionSettingsRequest;
import com.xfei.mailgun.model.domains.DomainsParametersFilter;
import com.xfei.mailgun.model.domains.SingleDomainResponse;
import com.xfei.mailgun.model.domains.UpdateDomainClickTrackingSettingsResponse;
import com.xfei.mailgun.model.domains.UpdateDomainConnectionResponse;
import com.xfei.mailgun.model.domains.UpdateDomainOpenTrackingSettingsResponse;
import com.xfei.mailgun.model.domains.UpdateDomainUnsubscribeTrackingSettingsResponse;
import com.xfei.mailgun.api.MailgunApi;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import feign.Response;

/**
 * <p>
 * The domains API allows you to create, access, and validate domains programmatically.
 * </p>
 *
 * @see <a href="https://documentation.mailgun.com/en/latest/api-domains.html">Domains</a>
 */
@Headers("Accept: application/json")
public interface MailgunDomainsApi extends MailgunApi {

    /**
     * <p>
     * Returns a list of domains under your account (limit to 100 entries).
     * </p>
     *
     * @return {@link com.xfei.mailgun.model.domains.DomainListResponse}
     */
    @RequestLine("GET /domains")
    DomainListResponse getDomainsList();

    /**
     * <p>
     * Returns a list of domains under your account (limit to 100 entries).
     * </p>
     *
     * @return {@link Response}
     */
    @RequestLine("GET /domains")
    Response getDomainsListFeignResponse();

    /**
     * <p>
     * Returns a list of domains under your account.
     * </p>
     *
     * @param filter {@link com.xfei.mailgun.model.domains.DomainsParametersFilter}
     * @return {@link DomainListResponse}
     */
    @RequestLine("GET /domains")
    DomainListResponse getDomainsList(@QueryMap DomainsParametersFilter filter);

    /**
     * <p>
     * Returns a list of domains under your account.
     * </p>
     *
     * @param filter {@link DomainsParametersFilter}
     * @return {@link Response}
     */
    @RequestLine("GET /domains")
    Response getDomainsListFeignResponse(@QueryMap DomainsParametersFilter filter);

    /**
     * <p>
     * Returns a single domain, including credentials and DNS records.
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link com.xfei.mailgun.model.domains.SingleDomainResponse}
     */
    @RequestLine("GET /domains/{domain}")
    SingleDomainResponse getSingleDomain(@Param("domain") String domain);

    /**
     * <p>
     * Returns a single domain, including credentials and DNS records.
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link Response}
     */
    @RequestLine("GET /domains/{domain}")
    Response getSingleDomainFeignResponse(@Param("domain") String domain);

    /**
     * <p>
     * Verifies and returns a single domain, including credentials and DNS records.
     * If the domain is successfully verified the domain’s state will be ‘active’.
     * For more information on verifying domains, visit: @see <a href="https://documentation.mailgun.com/en/latest/user_manual.html#verifying-your-domain">Verifying Your Domain</a>
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link com.xfei.mailgun.model.domains.DomainResponse}
     */
    @RequestLine("PUT /domains/{domain}/verify")
    DomainResponse verifyDomain(@Param("domain") String domain);

    /**
     * <p>
     * Verifies and returns a single domain, including credentials and DNS records.
     * If the domain is successfully verified the domain’s state will be ‘active’.
     * For more information on verifying domains, visit: @see <a href="https://documentation.mailgun.com/en/latest/user_manual.html#verifying-your-domain">Verifying Your Domain</a>
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link Response}
     */
    @RequestLine("PUT /domains/{domain}/verify")
    Response verifyDomainFeignResponse(@Param("domain") String domain);

    /**
     * <p>
     * Create a new domain.
     * </p>
     *
     * @param request {@link com.xfei.mailgun.model.domains.DomainRequest}
     * @return {@link DomainResponse}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("POST /domains")
    DomainResponse createNewDomain(DomainRequest request);

    /**
     * <p>
     * Create a new domain.
     * </p>
     *
     * @param request {@link DomainRequest}
     * @return {@link Response}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("POST /domains")
    Response createNewDomainFeignResponse(DomainRequest request);

    /**
     * <p>
     * Delete a domain from your account.
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link com.xfei.mailgun.model.ResponseWithMessage}
     */
    @RequestLine("DELETE /domains/{domain}")
    ResponseWithMessage deleteDomain(@Param("domain") String domain);

    /**
     * <p>
     * Delete a domain from your account.
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link Response}
     */
    @RequestLine("DELETE /domains/{domain}")
    Response deleteDomainFeignResponse(@Param("domain") String domain);

    /**
     * <p>
     * Creates a new set of SMTP credentials for the defined domain.
     * </p>
     *
     * @param domain  Name of the domain
     * @param request {@link com.xfei.mailgun.model.domains.DomainCredentials}
     * @return {@link ResponseWithMessage}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("POST /domains/{domain}/credentials")
    ResponseWithMessage createNewCredentials(@Param("domain") String domain, DomainCredentials request);

    /**
     * <p>
     * Creates a new set of SMTP credentials for the defined domain.
     * </p>
     *
     * @param domain  Name of the domain
     * @param request {@link DomainCredentials}
     * @return {@link Response}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("POST /domains/{domain}/credentials")
    Response createNewCredentialsFeignResponse(@Param("domain") String domain, DomainCredentials request);

    /**
     * <p>
     * Updates the specified SMTP credentials.
     * Currently only the password can be changed.
     * </p>
     *
     * @param domain   Name of the domain
     * @param login    Login
     * @param password A password for the SMTP credentials. (Length Min 5, Max 32)
     * @return {@link ResponseWithMessage}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/credentials/{login}")
    ResponseWithMessage updateCredentials(@Param("domain") String domain, @Param("login") String login, @Param("password") String password);

    /**
     * <p>
     * Updates the specified SMTP credentials.
     * Currently only the password can be changed.
     * </p>
     *
     * @param domain   Name of the domain
     * @param login    Login
     * @param password A password for the SMTP credentials. (Length Min 5, Max 32)
     * @return {@link Response}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/credentials/{login}")
    Response updateCredentialsFeignResponse(@Param("domain") String domain, @Param("login") String login, @Param("password") String password);

    /**
     * <p>
     * Deletes the defined SMTP credentials.
     * </p>
     *
     * @param domain Name of the domain
     * @param login  Login
     * @return {@link ResponseWithMessage}
     */
    @RequestLine("DELETE /domains/{domain}/credentials/{login}")
    ResponseWithMessage deleteCredentials(@Param("domain") String domain, @Param("login") String login);

    /**
     * <p>
     * Deletes the defined SMTP credentials.
     * </p>
     *
     * @param domain Name of the domain
     * @param login  Login
     * @return {@link Response}
     */
    @RequestLine("DELETE /domains/{domain}/credentials/{login}")
    Response deleteCredentialsFeignResponse(@Param("domain") String domain, @Param("login") String login);

    /**
     * <p>
     * Returns delivery connection settings for the defined domain.
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link com.xfei.mailgun.model.domains.DomainConnectionResponse}
     */
    @RequestLine("GET /domains/{domain}/connection")
    DomainConnectionResponse getDomainConnectionSettings(@Param("domain") String domain);

    /**
     * <p>
     * Returns delivery connection settings for the defined domain.
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link Response}
     */
    @RequestLine("GET /domains/{domain}/connection")
    Response getDomainConnectionSettingsFeignResponse(@Param("domain") String domain);

    /**
     * <p>
     * Updates the specified delivery connection settings for the defined domain.
     * </p>
     *
     * @param domain  Name of the domain
     * @param request {@link com.xfei.mailgun.model.domains.DomainConnectionRequest}
     * @return {@link com.xfei.mailgun.model.domains.UpdateDomainConnectionResponse}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/connection")
    UpdateDomainConnectionResponse updateDomainConnectionSettings(@Param("domain") String domain, DomainConnectionRequest request);

    /**
     * <p>
     * Updates the specified delivery connection settings for the defined domain.
     * </p>
     *
     * @param domain  Name of the domain
     * @param request {@link DomainConnectionRequest}
     * @return {@link Response}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/connection")
    Response updateDomainConnectionSettingsFeignResponse(@Param("domain") String domain, DomainConnectionRequest request);

    /**
     * <p>
     * Returns tracking settings for a domain.
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link com.xfei.mailgun.model.domains.DomainTrackingResponse}
     */
    @RequestLine("GET /domains/{domain}/tracking")
    DomainTrackingResponse getDomainTrackingSettings(@Param("domain") String domain);

    /**
     * <p>
     * Returns tracking settings for a domain.
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link Response}
     */
    @RequestLine("GET /domains/{domain}/tracking")
    Response getDomainTrackingSettingsFeignResponse(@Param("domain") String domain);

    /**
     * <p>
     * Updates the open tracking settings for a domain.
     * </p>
     *
     * @param domain Name of the domain
     * @param active {@link com.xfei.mailgun.enums.YesNo}
     * @return {@link com.xfei.mailgun.model.domains.UpdateDomainOpenTrackingSettingsResponse}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/tracking/open")
    UpdateDomainOpenTrackingSettingsResponse updateDomainOpenTrackingSettings(@Param("domain") String domain,
                                                                              @Param(value = "active", expander = EnumExpander.class) YesNo active);

    /**
     * <p>
     * Updates the open tracking settings for a domain.
     * </p>
     *
     * @param domain Name of the domain
     * @param active {@link YesNo}
     * @return {@link Response}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/tracking/open")
    Response updateDomainOpenTrackingSettingsFeignResponse(@Param("domain") String domain,
                                                           @Param(value = "active", expander = EnumExpander.class) YesNo active);

    /**
     * <p>
     * Updates the click tracking settings for a domain.
     * </p>
     *
     * @param domain Name of the domain
     * @param active {@link com.xfei.mailgun.enums.YesNoHtml}
     *               <p>
     *               If set to <code>YES</code>, links will be overwritten and pointed to our servers so we can track clicks.
     *               </p>
     *               <p>
     *               If set to <code>HTML_ONLY</code>, links will only be rewritten in the HTML part of a message.
     *               </p>
     * @return {@link com.xfei.mailgun.model.domains.UpdateDomainClickTrackingSettingsResponse}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/tracking/click")
    UpdateDomainClickTrackingSettingsResponse updateDomainClickTrackingSettings(@Param("domain") String domain,
                                                                                @Param(value = "active", expander = EnumExpander.class) YesNoHtml active);

    /**
     * <p>
     * Updates the click tracking settings for a domain.
     * </p>
     *
     * @param domain Name of the domain
     * @param active {@link YesNoHtml}
     *               <p>
     *               If set to <code>YES</code>, links will be overwritten and pointed to our servers so we can track clicks.
     *               </p>
     *               <p>
     *               If set to <code>HTML_ONLY</code>, links will only be rewritten in the HTML part of a message.
     *               </p>
     * @return {@link Response}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/tracking/click")
    Response updateDomainClickTrackingSettingsFeignResponse(@Param("domain") String domain,
                                                            @Param(value = "active", expander = EnumExpander.class) YesNoHtml active);

    /**
     * <p>
     * Updates unsubscribe tracking settings for a domain.
     * </p>
     *
     * @param domain  Name of the domain
     * @param request {@link com.xfei.mailgun.model.domains.DomainUnsubscribeConnectionSettingsRequest}
     * @return {@link com.xfei.mailgun.model.domains.UpdateDomainUnsubscribeTrackingSettingsResponse}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/tracking/unsubscribe")
    UpdateDomainUnsubscribeTrackingSettingsResponse updateDomainUnsubscribeConnectionSettings(@Param("domain") String domain, DomainUnsubscribeConnectionSettingsRequest request);

    /**
     * <p>
     * Updates unsubscribe tracking settings for a domain.
     * </p>
     *
     * @param domain  Name of the domain
     * @param request {@link DomainUnsubscribeConnectionSettingsRequest}
     * @return {@link Response}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/tracking/unsubscribe")
    Response updateDomainUnsubscribeConnectionSettingsFeignResponse(@Param("domain") String domain, DomainUnsubscribeConnectionSettingsRequest request);

}
