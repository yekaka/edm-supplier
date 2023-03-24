package com.edm.mailgun.api.v3;

import com.edm.mailgun.enums.WebhookName;
import com.edm.mailgun.expanders.EnumExpander;
import com.edm.mailgun.model.webhooks.WebhookDetailsResult;
import com.edm.mailgun.model.webhooks.WebhookListResult;
import com.edm.mailgun.model.webhooks.WebhookRequest;
import com.edm.mailgun.model.webhooks.WebhookResult;
import com.edm.mailgun.model.webhooks.WebhookUpdateRequest;
import com.edm.mailgun.api.MailgunApi;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

/**
 * <p>
 * Webhooks API allows you to create, access, and delete webhooks programmatically.
 * </p>
 *
 * @see <a href="https://documentation.mailgun.com/en/latest/api-webhooks.html">Webhooks</a>
 */
@Headers("Accept: application/json")
public interface MailgunWebhooksApi extends MailgunApi {

    /**
     * <p>
     * Returns a list of webhooks set for the specified domain.
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link com.edm.mailgun.model.webhooks.WebhookListResult}
     */
    @RequestLine("GET /domains/{domain}/webhooks")
    WebhookListResult getAllWebhooks(@Param("domain") String domain);

    /**
     * <p>
     * Returns a list of webhooks set for the specified domain.
     * </p>
     *
     * @param domain Name of the domain
     * @return {@link Response}
     */
    @RequestLine("GET /domains/{domain}/webhooks")
    Response getAllWebhooksFeignResponse(@Param("domain") String domain);

    /**
     * <p>
     * Return details about a webhook specified in the URL.
     * </p>
     *
     * @param domain      Name of the domain
     * @param webhookName Name of the webhook.
     * @return {@link com.edm.mailgun.model.webhooks.WebhookDetailsResult}
     */
    @RequestLine("GET /domains/{domain}/webhooks/{name}")
    WebhookDetailsResult getWebhookDetails(@Param("domain") String domain, @Param(value = "name", expander = EnumExpander.class) WebhookName webhookName);

    /**
     * <p>
     * Return details about a webhook specified in the URL.
     * </p>
     *
     * @param domain      Name of the domain
     * @param webhookName Name of the webhook.
     * @return {@link Response}
     */
    @RequestLine("GET /domains/{domain}/webhooks/{name}")
    Response getWebhookDetailsFeignResponse(@Param("domain") String domain, @Param(value = "name", expander = EnumExpander.class) WebhookName webhookName);

    /**
     * <p>
     * Creates a new webhook.
     * </p>
     * Warning: When adding a Clicked or Opened webhook, ensure that you also have tracking enabled.
     *
     * @param domain  Name of the domain
     * @param request {@link com.edm.mailgun.model.webhooks.WebhookRequest}
     * @return {@link com.edm.mailgun.model.webhooks.WebhookResult}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("POST /domains/{domain}/webhooks")
    WebhookResult createNewWebhook(@Param("domain") String domain, WebhookRequest request);

    /**
     * <p>
     * Creates a new webhook.
     * Warning: When adding a Clicked or Opened webhook, ensure that you also have tracking enabled.
     * </p>
     *
     * @param domain  Name of the domain
     * @param request {@link WebhookRequest}
     * @return {@link Response}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("POST /domains/{domain}/webhooks")
    Response createNewWebhookFeignResponse(@Param("domain") String domain, WebhookRequest request);

    /**
     * <p>
     * Updates an existing webhook.
     * </p>
     *
     * @param domain      Name of the domain
     * @param webhookName Name of the webhook.
     * @param request     {@link com.edm.mailgun.model.webhooks.WebhookUpdateRequest}
     * @return {@link WebhookResult}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/webhooks/{name}")
    WebhookResult updateWebhook(@Param("domain") String domain, @Param(value = "name", expander = EnumExpander.class) WebhookName webhookName, WebhookUpdateRequest request);

    /**
     * <p>
     * Updates an existing webhook.
     * </p>
     *
     * @param domain      Name of the domain
     * @param webhookName Name of the webhook.
     * @param request     {@link WebhookUpdateRequest}
     * @return {@link Response}
     */
    @Headers("Content-Type: multipart/form-data")
    @RequestLine("PUT /domains/{domain}/webhooks/{name}")
    Response updateWebhookFeignResponse(@Param("domain") String domain, @Param(value = "name", expander = EnumExpander.class) WebhookName webhookName, WebhookUpdateRequest request);

    /**
     * <p>
     * Deletes an existing webhook.
     * </p>
     *
     * @param domain      Name of the domain
     * @param webhookName Name of the webhook.
     * @return {@link WebhookResult}
     */
    @RequestLine("DELETE /domains/{domain}/webhooks/{name}")
    WebhookResult deleteWebhook(@Param("domain") String domain, @Param(value = "name", expander = EnumExpander.class) WebhookName webhookName);

    /**
     * <p>
     * Deletes an existing webhook.
     * </p>
     *
     * @param domain      Name of the domain
     * @param webhookName Name of the webhook.
     * @return {@link Response}
     */
    @RequestLine("DELETE /domains/{domain}/webhooks/{name}")
    Response deleteWebhookFeignResponse(@Param("domain") String domain, @Param(value = "name", expander = EnumExpander.class) WebhookName webhookName);

}
