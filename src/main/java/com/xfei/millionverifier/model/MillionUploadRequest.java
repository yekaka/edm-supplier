package com.xfei.millionverifier.model;

import feign.form.FormData;
import feign.form.FormProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 批量邮箱的校验返回信息 （支持 .txt / .csv / .xlsx）
 *
 * @author yyy
 * @date 2023/03/24 20:53
 */
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class MillionUploadRequest {

    @FormProperty("file_contents")
    FormData formData;
}

