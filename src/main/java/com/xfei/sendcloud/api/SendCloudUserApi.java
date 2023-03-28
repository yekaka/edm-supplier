package com.xfei.sendcloud.api;

import com.xfei.sendcloud.model.SendCloudBaseResponse;
import com.xfei.sendcloud.model.send.SendCloudSendRequest;
import com.xfei.sendcloud.model.user.SendCloudSenderAddRequest;
import com.xfei.sendcloud.model.user.SendCloudSenderDeleteRequest;
import com.xfei.sendcloud.model.user.SendCloudSenderRequest;
import feign.Headers;
import feign.RequestLine;

/**
 * sendCloud-用户设置（固定发件人） api
 *
 * @author yzq
 * @date 2023/03/26 15:10
 */
@Headers({"Content-Type: multipart/form-data", "Accept: application/json"})
public interface SendCloudUserApi {
    //查询发件人
    @RequestLine("POST /sender/list")
    SendCloudBaseResponse<?> list(SendCloudSenderRequest request);

    //固定发件人
    @RequestLine("POST /sender/add")
    SendCloudBaseResponse<?> add(SendCloudSenderAddRequest request);

    //删除发件人
    @RequestLine("POST /sender/delete")
    SendCloudBaseResponse<?> delete(SendCloudSenderDeleteRequest request);
}
