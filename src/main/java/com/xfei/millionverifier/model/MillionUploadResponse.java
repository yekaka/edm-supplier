package com.xfei.millionverifier.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * descThisFile
 *
 * @author yzq
 * @date 2023/03/24 19:18
 */
@Value
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MillionUploadResponse {
    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("file_name")
    private String fileName;

    /**
     * in_progress/
     */
    private String status;

    @JsonProperty("unique_emails")
    private Integer uniqueEmails;

    @JsonProperty("updated_at")
    private String updatedAt;

    private String createdate;

    private Integer percent;

    @JsonProperty("total_rows")
    private Integer totalRows;

    private Integer verified;

    private Integer unverified;

    private Integer ok;

    @JsonProperty("catch_all")
    private Integer catchAll;

    private Integer disposable;

    private Integer invalid;

    private Integer unknown;

    private Integer reverify;

    private Integer credit;

    @JsonProperty("estimated_time_sec")
    private Integer estimatedTimeSec;
}
