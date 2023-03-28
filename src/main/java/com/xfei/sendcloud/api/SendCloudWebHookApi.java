package com.xfei.sendcloud.api;

import com.xfei.sendcloud.model.SendCloudBaseResponse;
import com.xfei.sendcloud.model.send.SendCloudSendRequest;
import com.xfei.sendcloud.model.webhook.SendCloudWebHookRequest;
import com.xfei.sendcloud.model.webhook.SendCloudWebHookUpdateRequest;
import feign.Headers;
import feign.RequestLine;

/**
 * sendCloud-webhook操作 api
 *
 * @author yzq
 * @date 2023/03/26 15:12
 */
@Headers({"Content-Type: multipart/form-data", "Accept: application/json"})
public interface SendCloudWebHookApi {
    //查询WebHook
    @RequestLine("POST /webhook/list")
    SendCloudBaseResponse<?> list(SendCloudWebHookRequest request);

    //创建WebHook
    @RequestLine("POST /webhook/add")
    SendCloudBaseResponse<?> add(SendCloudWebHookRequest request);

    //更新WebHook
    @RequestLine("POST /webhook/update")
    SendCloudBaseResponse<?> update(SendCloudWebHookUpdateRequest request);

    //删除WebHook
    @RequestLine("POST /webhook/delete")
    SendCloudBaseResponse<?> delete(SendCloudWebHookRequest request);
}
