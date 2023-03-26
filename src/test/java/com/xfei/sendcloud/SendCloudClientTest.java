package com.xfei.sendcloud;

import com.xfei.sendcloud.api.SendCloudDomainsApi;
import com.xfei.sendcloud.client.SendCloudClient;
import com.xfei.sendcloud.model.SendCloudBaseResponse;
import com.xfei.sendcloud.model.domains.SendCloudDomainAddRequest;
import com.xfei.sendcloud.model.domains.SendCloudDomainRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * sendCloud测试；(测试发现该服务商的接口 查询数据都存在缓存；这边注意下)
 *
 * @author yzq
 * @date 2023/03/24 20:45
 */
class SendCloudClientTest {
    private SendCloudDomainsApi sendCloudDomainsApi;

    private final String apiKey = "xxx";
    private final String apiUser = "xxx";

    @BeforeEach
    void setUp() {
        sendCloudDomainsApi = SendCloudClient.config()
                .createApiWithAbsoluteUrl(SendCloudDomainsApi.class);
    }


    @Test
    void domainList() {
        SendCloudDomainRequest request = SendCloudDomainRequest.builder()
                .apiKey(apiKey)
                .apiUser(apiUser)
                .name("yy.yyyiii.com")
                .build();
        SendCloudBaseResponse<?> response = sendCloudDomainsApi.list(request);
        System.out.println(response.toString());
    }

    @Test
    void domainAdd() {
        SendCloudDomainAddRequest request = SendCloudDomainAddRequest.builder()
                .apiKey(apiKey)
                .apiUser(apiUser)
                .name("yy.yyyiii.com")
                .build();
        SendCloudBaseResponse<?> response = sendCloudDomainsApi.add(request);
        System.out.println(response.toString());
    }

}
