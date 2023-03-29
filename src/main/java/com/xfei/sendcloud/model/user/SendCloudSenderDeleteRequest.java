package com.xfei.sendcloud.model.user;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 删除固定发件人
 *
 * @author yyy
 * @date 2023/03/28 20:01
 */
@Data
@ToString
@EqualsAndHashCode
@Builder
public class SendCloudSenderDeleteRequest {
    @FormProperty("apiUser")
    String apiUser;

    @FormProperty("apiKey")
    String apiKey;

    @FormProperty("id")
    Integer id;
}
