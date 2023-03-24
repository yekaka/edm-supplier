package com.xfei.mailgun.model.domains;

import com.xfei.mailgun.constants.TestConstants;
import com.xfei.mailgun.enums.SpamAction;
import com.xfei.mailgun.enums.WebScheme;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomainRequestTest {

    @Test
    void builderSuccessTest() {
        DomainRequest request = DomainRequest.builder()
                .name(TestConstants.TEST_DOMAIN)
                .spamAction(SpamAction.BLOCK)
                .wildcard(true)
                .forceDkimAuthority(false)
                .dkimKeySize(1024)
                .ips(Arrays.asList(TestConstants.TEST_IP_1, TestConstants.TEST_IP_2))
                .webScheme(WebScheme.HTTPS)
                .build();

        assertEquals(TestConstants.TEST_DOMAIN, request.getName());
        assertEquals(SpamAction.BLOCK.getValue(), request.getSpamAction());
        assertEquals(true, request.getWildcard());
        assertEquals(false, request.getForceDkimAuthority());
        assertEquals(1024, request.getDkimKeySize());
        assertTrue(request.getIps().containsAll(Arrays.asList(TestConstants.TEST_IP_1, TestConstants.TEST_IP_2)));
        assertEquals(WebScheme.HTTPS.getValue(), request.getWebScheme());
    }

}
