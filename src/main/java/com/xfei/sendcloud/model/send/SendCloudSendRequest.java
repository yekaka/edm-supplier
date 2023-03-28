package com.xfei.sendcloud.model.send;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 邮件发送主体信息
 *
 * @author yzq
 * @date 2023/03/28 19:08
 */
@Data
@ToString
@EqualsAndHashCode
@Builder
public class SendCloudSendRequest {
    @FormProperty("apiUser")
    String apiUser;

    @FormProperty("apiKey")
    String apiKey;

    @FormProperty("from")
    String from;

    @FormProperty("subject")
    String subject;

    @FormProperty("fromName")
    String fromName;

    /**
     * 设置用户默认的回复邮件地址.多个地址使用';'分隔，地址个数不能超过3个. 如果 replyTo 没有或者为空, 则默认的回复邮件地址为 from
     */
    @FormProperty("replyTo")
    String replyTo;

    /**
     * 正文
     */
    @FormProperty("html")
    String html;

    /**
     * 邮件头部信息. JSON 格式, 比如:{"header1": "value1", "header2": "value2"} --主要为了放 邮件+用户信息；webhook会返回
     */
    @FormProperty("headers")
    String headers;

    /**
     * SMTP 扩展字段. JSON格式 如 {"to": ["support@ifaxin.com"],"sub":{}}   to--收件人集合； sub--自定义宏信息（%xxx%）
     */
    @FormProperty("xsmtpapi")
    String xsmtpapi;

    /**
     * 默认值: true. 是否返回 emailId. 有多个收件人时, 会返回 emailId 的列表
     */
    @FormProperty("respEmailId")
    boolean respEmailId = true;

    public SendCloudSendRequest() {
    }
}
