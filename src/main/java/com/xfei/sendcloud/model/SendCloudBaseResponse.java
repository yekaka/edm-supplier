package com.xfei.sendcloud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * descThisFile
 *
 * @author yyy
 * @date 2023/03/26 16:17
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendCloudBaseResponse<T> {
    @JsonProperty("result")
    Boolean result;

    @JsonProperty("statusCode")
    Integer statusCode;

    @JsonProperty("message")
    String message;

    @JsonProperty("info")
    T info;
}
