package com.edm.mailgun.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@UtilityClass
public class DateTimeUtil {

    private static final String UNABLE_TO_FORMAT_PROVIDED_ZONED_DATE_TIME = "Unable to format provided ZonedDateTime";

    private static final DateTimeFormatter FORMATTER_DATE_TIME_PATTERN_TIME_ZONE = DateTimeFormatter
        .ofPattern(Constants.RFC_2822_DATE_TIME_PATTERN_TIME_ZONE_NUMERIC, Locale.ENGLISH);
    private static final DateTimeFormatter FORMATTER_DATE_TIME_TIME_ZONE_NAME_DAY = DateTimeFormatter
        .ofPattern(Constants.RFC_2822_DATE_TIME_PATTERN_TIME_ZONE_NAME_DAY_OCTAL_LITERAL, Locale.ENGLISH);

    public static String toString(ZonedDateTime zonedDateTime) {
        try {
            return zonedDateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME);
        } catch (Exception e) {
            log.error(UNABLE_TO_FORMAT_PROVIDED_ZONED_DATE_TIME, e);
        }
        return null;
    }

    public static String toStringNumericTimeZone(ZonedDateTime zonedDateTime) {
        try {
            return zonedDateTime.format(FORMATTER_DATE_TIME_PATTERN_TIME_ZONE);
        } catch (Exception e) {
            log.error(UNABLE_TO_FORMAT_PROVIDED_ZONED_DATE_TIME, e);
        }
        return null;
    }

    public static String toStringNameTimeZone(ZonedDateTime zonedDateTime) {
        try {
            return zonedDateTime.format(FORMATTER_DATE_TIME_TIME_ZONE_NAME_DAY);
        } catch (Exception e) {
            log.error(UNABLE_TO_FORMAT_PROVIDED_ZONED_DATE_TIME, e);
        }
        return null;
    }

}
