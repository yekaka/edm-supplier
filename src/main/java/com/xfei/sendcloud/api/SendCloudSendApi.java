package com.xfei.sendcloud.api;

import com.xfei.sendcloud.model.SendCloudBaseResponse;
import com.xfei.sendcloud.model.send.SendCloudSendRequest;
import feign.Headers;
import feign.RequestLine;

/**
 * sendCloud-发送操作 api
 *
 * @author yzq
 * @date 2023/03/26 15:10
 */
@Headers({"Content-Type: multipart/form-data", "Accept: application/json"})
public interface SendCloudSendApi {

    @RequestLine("POST /mail/send")
    SendCloudBaseResponse<?> send(SendCloudSendRequest request);


}
