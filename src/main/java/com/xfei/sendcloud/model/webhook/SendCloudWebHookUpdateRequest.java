package com.xfei.sendcloud.model.webhook;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * descThisFile
 *
 * @author yyy
 * @date 2023/03/28 19:42
 */
@Data
@ToString
@EqualsAndHashCode
@Builder
public class SendCloudWebHookUpdateRequest {
    @FormProperty("apiUser")
    String apiUser;

    @FormProperty("apiKey")
    String apiKey;

    /**
     * 所产生的消息Post到Url
     */
    @FormProperty("url")
    String url;

    /**
     * 指定的API_USER
     */
    @FormProperty("categoryName")
    String categoryName;

    /**
     * 事件类型，比如1，3多个用逗号隔开，不填写默认全部
     * 1	送达(Delivered)	邮件发送成功
     * 3	垃圾邮件举报(report_spam)	用户举报邮件
     * 4	无效邮件(Invalid)	邮件未发送成功
     * 5	软退信(Soft Bounce)	接收方拒收该邮件
     * 10	点击(Click)	用户点击链接
     * 11	打开(Open)	用户打开邮件
     * 12	取消订阅(Unsubcribes)	用户取消订阅邮件
     * 18	请求(Request)	邮件请求成功
     */
    @FormProperty("event")
    String event;

    /**
     * 新的指定的API_USER
     */
    @FormProperty("newCategoryName")
    String newCategoryName;

    /**
     * 新的消息推送Url
     */
    @FormProperty("newUrl")
    String newUrl;

    /**
     * 新的触发webhook推送的事件
     */
    @FormProperty("newEvent")
    String newEvent;
}
