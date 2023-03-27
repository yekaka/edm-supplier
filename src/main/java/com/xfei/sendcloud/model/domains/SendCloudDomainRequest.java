package com.xfei.sendcloud.model.domains;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * sendCloud-域名查询
 *
 * @author yzq
 * @date 2023/03/26 15:44
 */

@Data
@ToString
@EqualsAndHashCode
@Builder
public class SendCloudDomainRequest  {
    /**
     * 域名名称. 多个 name 用 ; 分隔
     */
    @FormProperty("name")
    String name;

    /**
     * 域名类型: 0 (测试域名), 1(普通域名)
     * 没啥用--可忽略
     */
    @FormProperty("type")
    Integer type;

    /**
     * 域名验证值. 举例: verify=1, 查询验证值为 1 的域名; verify 为 ">22", 查询验证值大于等于 22 的域名; verify为 "<12", 查询验证值小于等于 12 的域名
     * 没啥用--可忽略
     */
    @FormProperty("verify")
    String verify;

    @FormProperty("apiUser")
    String apiUser;

    @FormProperty("apiKey")
    String apiKey;
}
