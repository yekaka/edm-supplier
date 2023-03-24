package com.xfei.mailgun.model.templates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xfei.mailgun.model.Paging;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * <p>
 * Template with all versions.
 * </p>
 *
 * @see <a href="https://documentation.mailgun.com/en/latest/api-templates.html">Templates</a>
 */
@Value
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateAllVersionsResponse {

    /**
     * <p>
     * {@link TemplateWithVersions}.
     * </p>
     */
    TemplateWithVersions template;

    /**
     * <p>
     * Provides pagination urls.
     * </p>
     * {@link Paging}
     */
    Paging paging;

}
