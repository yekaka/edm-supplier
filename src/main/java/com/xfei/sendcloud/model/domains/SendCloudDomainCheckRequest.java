package com.xfei.sendcloud.model.domains;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * sendCloud-域名校验
 *
 * @author yzq
 * @date 2023/03/26 15:44
 */

@Data
@ToString
@EqualsAndHashCode
@Builder
public class SendCloudDomainCheckRequest {
    /**
     * 域名名称. 多个 name 用 ; 分隔
     */
    @FormProperty("name")
    String name;

    @FormProperty("apiUser")
    String apiUser;

    @FormProperty("apiKey")
    String apiKey;
}
