package com.xfei.mailgun.model.templates;

import com.xfei.mailgun.constants.IntegrationTestConstants;
import com.xfei.mailgun.enums.YesNo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateVersionRequestTest {

    @Test
    void builderSuccessTest() {
        TemplateVersionRequest request = TemplateVersionRequest.builder()
                .template(IntegrationTestConstants.TEMPLATE_2)
                .tag(IntegrationTestConstants.TEMPLATE_VERSION_TAG_2)
                .engine(IntegrationTestConstants.TEMPLATE_ENGINE)
                .comment(IntegrationTestConstants.TEMPLATE_COMMENT)
                .active(true)
                .build();

        assertEquals(IntegrationTestConstants.TEMPLATE_2, request.getTemplate());
        assertEquals(IntegrationTestConstants.TEMPLATE_VERSION_TAG_2, request.getTag());
        assertEquals(IntegrationTestConstants.TEMPLATE_ENGINE, request.getEngine());
        assertEquals(IntegrationTestConstants.TEMPLATE_COMMENT, request.getComment());
        assertEquals(YesNo.YES.getValue(), request.getActive());
    }

}
