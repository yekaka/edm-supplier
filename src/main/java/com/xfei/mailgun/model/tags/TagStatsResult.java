package com.xfei.mailgun.model.tags;

import com.xfei.mailgun.model.stats.Stats;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xfei.mailgun.enums.ResolutionPeriod;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;
import java.util.List;

import static com.xfei.mailgun.util.Constants.ENGLISH;
import static com.xfei.mailgun.util.Constants.RFC_2822_DATE_TIME_PATTERN_TIME_ZONE_NAME;

/**
 * <p>
 * Statistics for a tag.
 * </p>
 *
 * @see <a href="https://documentation.mailgun.com/en/latest/api-tags.html#tags">Tags</a>
 */
@Value
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagStatsResult {

    /**
     * <p>
     * Name of the tag.
     * </p>
     */
    String tag;

    /**
     * <p>
     * Optional description of a tag.
     * </p>
     */
    String description;

    /**
     * <p>
     * The starting time.
     * </p>
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RFC_2822_DATE_TIME_PATTERN_TIME_ZONE_NAME, locale = ENGLISH)
    ZonedDateTime start;

    /**
     * <p>
     * The ending date.
     * </p>
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RFC_2822_DATE_TIME_PATTERN_TIME_ZONE_NAME, locale = ENGLISH)
    ZonedDateTime end;

    /**
     * <p>
     * Resolution
     * </p>
     * {@link ResolutionPeriod}
     * <p>
     * Can be either <code>HOUR</code>, <code>DAY</code> or <code>MONTH</code>
     * </p>
     */
    ResolutionPeriod resolution;

    /**
     * <p>
     * Statistics.
     * </p>
     * {@link com.xfei.mailgun.model.stats.Stats}
     */
    List<Stats> stats;

}
