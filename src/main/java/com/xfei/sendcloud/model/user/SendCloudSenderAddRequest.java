package com.xfei.sendcloud.model.user;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 新增固定发件人
 *
 * @author yyy
 * @date 2023/03/28 19:54
 */
@Data
@ToString
@EqualsAndHashCode
@Builder
public class SendCloudSenderAddRequest {
    @FormProperty("apiUser")
    String apiUser;

    @FormProperty("apiKey")
    String apiKey;

    /**
     * 指定API_USER，all 代表所有API_USER
     */
    @FormProperty("categoryName")
    String categoryName;

    /**
     * 收件域名，比如 qq.com，163.com；多个收件域名用逗号隔开，不传参或不传值则为全部域名
     */
    @FormProperty("receivingDomain")
    String receivingDomain;

    /**
     * 1.fromStatus=0，from 不填写
     *
     * 2.fromStatus=1，则实际值是传入的 from 的前缀。例如 API传入from=notice@sc.gg ，系统将使用 notice@sendingDomain作为from发信，from 不填写
     *
     * 3.fromStatus=2，则 from将以 固定值@sendingDomain 组成 email地址，from 为必填
     */
    @FormProperty("from")
    String from;

    /**
     * 1.mailfromStatus=0，则 mailfrom 将以 随机串@sendingDomain 组成 email地址，maiFrom 不填写
     *
     * 2.mailfromStatus=1，则 mailFrom 跟随from值，可去除代发，maiFrom 不填写
     *
     * 3.mailfromStatus=2，则 mailFrom 将以 固定值@sendingDomain 组成 email地址，maiFrom 为必填
     */
    @FormProperty("mailfrom")
    String mailfrom;

    /**
     * 0(不设置)，1(实际值)，2(固定值)，默认值1
     */
    @FormProperty("fromStatus")
    Integer fromStatus;

    /**
     * 0(不设置)，1(去代发)，2(固定值)，默认值1
     */
    @FormProperty("mailfromStatus")
    Integer mailfromStatus;
}
