package com.edm.mailgun.model.templates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * <p>
 * Template version tag.
 * </p>
 *
 * @see <a href="https://documentation.mailgun.com/en/latest/api-templates.html">Templates</a>
 */
@Value
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateVersionTag {

    /**
     * <p>
     * Name of the template.
     * </p>
     */
    String name;

    /**
     * <p>
     * {@link VersionTag}.
     * </p>
     */
    VersionTag version;

}
