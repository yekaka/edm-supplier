package com.xfei.millionverifier.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * 单个邮箱的校验返回信息
 *
 * @author yzq
 * @date 2023/03/24 20:53
 */
@Value
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MillionSingleResponse {
    @JsonProperty("email")
    String email;
    @JsonProperty("quality")
    String quality;
    @JsonProperty("result")
    String result;
    @JsonProperty("resultcode")
    Integer resultcode;
    @JsonProperty("subresult")
    String subresult;
    @JsonProperty("free")
    Boolean free;
    @JsonProperty("role")
    Boolean role;
    @JsonProperty("didyoumean")
    String didyoumean;
    @JsonProperty("credits")
    Integer credits;
    @JsonProperty("executiontime")
    Integer executiontime;
    @JsonProperty("error")
    String error;
    @JsonProperty("livemode")
    Boolean livemode;
}
