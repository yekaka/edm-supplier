package com.edm.mailgun.model.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * <p>
 * Single Routes response.
 * </p>
 *
 * @see <a href="https://documentation.mailgun.com/en/latest/api-routes.html#routes">Routes</a>
 */
@Value
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleRouteResponse {

    /**
     * <p>
     * {@link Route}.
     * </p>
     */
    Route route;

}
