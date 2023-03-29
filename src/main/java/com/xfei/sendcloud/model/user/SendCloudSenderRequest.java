package com.xfei.sendcloud.model.user;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 固定发件人查询
 *
 * @author yyy
 * @date 2023/03/28 19:54
 */
@Data
@ToString
@EqualsAndHashCode
@Builder
public class SendCloudSenderRequest {
    @FormProperty("apiUser")
    String apiUser;

    @FormProperty("apiKey")
    String apiKey;

    /**
     * 查询起始位置，取值区间 [0-]，默认为 0
     */
    @FormProperty("start")
    Integer start;

    /**
     * 查询个数， 取值区间 [0-100]，默认为 100
     */
    @FormProperty("limit")
    Integer limit;
}
