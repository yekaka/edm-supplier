package com.edm.mailgun.model.templates;

import com.edm.mailgun.constants.IntegrationTestConstants;
import com.edm.mailgun.enums.YesNo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateTemplateVersionRequestTest {

    @Test
    void builderSuccessTest() {
        UpdateTemplateVersionRequest request = UpdateTemplateVersionRequest.builder()
                .template(IntegrationTestConstants.TEMPLATE_2)
                .comment(IntegrationTestConstants.TEMPLATE_COMMENT)
                .active(true)
                .build();

        assertEquals(IntegrationTestConstants.TEMPLATE_2, request.getTemplate());
        assertEquals(IntegrationTestConstants.TEMPLATE_COMMENT, request.getComment());
        assertEquals(YesNo.YES.getValue(), request.getActive());
    }

}
