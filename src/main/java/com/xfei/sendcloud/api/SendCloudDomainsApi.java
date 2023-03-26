package com.xfei.sendcloud.api;

import com.xfei.sendcloud.model.SendCloudBaseResponse;
import com.xfei.sendcloud.model.domains.SendCloudDomainRequest;
import feign.Headers;
import feign.RequestLine;

/**
 * sendCloud-域名操作 api
 *
 * @author yzq
 * @date 2023/03/26 15:09
 */
@Headers("Accept: application/json")
public interface SendCloudDomainsApi {

    @Headers("Content-Type: multipart/form-data")
    @RequestLine("POST /domain/list")
    SendCloudBaseResponse<?> list(SendCloudDomainRequest request);

}
