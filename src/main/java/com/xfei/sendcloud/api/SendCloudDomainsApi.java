package com.xfei.sendcloud.api;

import com.xfei.sendcloud.model.SendCloudBaseResponse;
import com.xfei.sendcloud.model.domains.SendCloudDomainAddRequest;
import com.xfei.sendcloud.model.domains.SendCloudDomainCheckRequest;
import com.xfei.sendcloud.model.domains.SendCloudDomainRequest;
import com.xfei.sendcloud.model.domains.SendCloudDomainUpdateRequest;
import feign.Headers;
import feign.RequestLine;

/**
 * sendCloud-域名操作 api
 *
 * @author yzq
 * @date 2023/03/26 15:09
 */
@Headers({"Content-Type: multipart/form-data", "Accept: application/json"})
public interface SendCloudDomainsApi {

    @RequestLine("POST /domain/list")
    SendCloudBaseResponse<?> list(SendCloudDomainRequest request);

    @RequestLine("POST /domain/add")
    SendCloudBaseResponse<?> add(SendCloudDomainAddRequest request);

    @RequestLine("POST /domain/update")
    SendCloudBaseResponse<?> update(SendCloudDomainUpdateRequest request);

    @RequestLine("POST /domain/checkConfig")
    SendCloudBaseResponse<?> checkConfig(SendCloudDomainCheckRequest request);
}
