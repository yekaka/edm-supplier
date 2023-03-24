package com.edm.mailgun.model.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * <p>
 * Domain tracking settings statuses.
 * </p>
 *
 * @see <a href="https://documentation.mailgun.com/en/latest/api-domains.html#domains">Domains</a>
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainTracking {

    /**
     * <p>
     * Domain click tracking settings status.
     * </p>
     * {@link DomainTrackingStatus}
     */
    DomainTrackingStatus click;

    /**
     * <p>
     * Domain open tracking settings status.
     * </p>
     * {@link DomainTrackingStatus}
     */
    DomainTrackingStatus open;

    /**
     * <p>
     * Domain unsubscribe tracking settings status.
     * </p>
     * {@link DomainTrackingUnsubscribeStatus}
     */
    DomainTrackingUnsubscribeStatus unsubscribe;

}
