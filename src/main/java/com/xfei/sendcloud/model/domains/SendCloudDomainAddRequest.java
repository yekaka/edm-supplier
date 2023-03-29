package com.xfei.sendcloud.model.domains;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * sendCloud-域名添加
 *
 * @author yyy
 * @date 2023/03/26 15:44
 */

@Data
@ToString
@EqualsAndHashCode
@Builder
public class SendCloudDomainAddRequest {
    /**
     * 域名名称. 只能是单个
     */
    @FormProperty("name")
    String name;

    @FormProperty("apiUser")
    String apiUser;

    @FormProperty("apiKey")
    String apiKey;
}
