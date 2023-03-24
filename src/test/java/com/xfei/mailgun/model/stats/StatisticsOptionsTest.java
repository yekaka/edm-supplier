package com.xfei.mailgun.model.stats;

import com.xfei.mailgun.constants.TestConstants;
import com.xfei.mailgun.enums.Duration;
import com.xfei.mailgun.enums.ResolutionPeriod;
import com.xfei.mailgun.enums.StatsEventType;
import com.xfei.mailgun.model.StatisticsOptions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Locale;

import static com.xfei.util.Constants.DURATION_MUST_BE_GREATER_THAN_ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatisticsOptionsTest {

    @Test
    void createStatsOptionsEventNullTest() {
        StatisticsOptions.StatisticsOptionsBuilder statsOptionsBuilder = StatisticsOptions.builder();

        Exception exception = assertThrows(NullPointerException.class, statsOptionsBuilder::build);

        assertEquals("event is marked non-null but is null", exception.getMessage());
    }

    @Test
    void createStatsOptionsDurationErrorTest() {
        StatisticsOptions.StatisticsOptionsBuilder statsOptionsBuilder = StatisticsOptions.builder();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> statsOptionsBuilder
                        .duration(0, Duration.HOUR));

        assertEquals(String.format(DURATION_MUST_BE_GREATER_THAN_ZERO, 0), exception.getMessage());
    }

    @Test
    void createStatsOptionsTimeRangeSuccessTest() {
        StatisticsOptions result = StatisticsOptions.builder()
                .event(StatsEventType.OPENED)
                .event(Arrays.asList(StatsEventType.ACCEPTED, StatsEventType.DELIVERED))
                .start(TestConstants.ZONED_DATE_TIME_2018_2_3_GMT)
                .end(TestConstants.ZONED_DATE_TIME_2020_4_5_GMT)
                .build();

        assertNotNull(result);
        assertEquals(new LinkedHashSet<>(Arrays.asList(StatsEventType.OPENED.getValue(), StatsEventType.ACCEPTED.getValue(), StatsEventType.DELIVERED.getValue())), result.getEvent());
        assertEquals(TestConstants.ZONED_DATE_TIME_2018_2_3_STRING, result.getStart());
        assertEquals(TestConstants.ZONED_DATE_TIME_2020_4_5_STRING, result.getEnd());
    }

    @Test
    void createStatsOptionsTimeRange_GermanyLocale_SuccessTest() {
        Locale.setDefault(Locale.GERMANY);

        StatisticsOptions result = StatisticsOptions.builder()
            .event(StatsEventType.OPENED)
            .event(Arrays.asList(StatsEventType.ACCEPTED, StatsEventType.DELIVERED))
            .start(TestConstants.ZONED_DATE_TIME_2018_2_3_GMT)
            .end(TestConstants.ZONED_DATE_TIME_2020_4_5_GMT)
            .build();

        assertNotNull(result);
        assertEquals(new LinkedHashSet<>(Arrays.asList(StatsEventType.OPENED.getValue(), StatsEventType.ACCEPTED.getValue(), StatsEventType.DELIVERED.getValue())), result.getEvent());
        assertEquals(TestConstants.ZONED_DATE_TIME_2018_2_3_STRING, result.getStart());
        assertEquals(TestConstants.ZONED_DATE_TIME_2020_4_5_STRING, result.getEnd());
    }

    @Test
    void createStatsOptionsDurationSuccessTest() {
        StatisticsOptions result = StatisticsOptions.builder()
                .event(StatsEventType.OPENED)
                .event(Arrays.asList(StatsEventType.ACCEPTED, StatsEventType.DELIVERED))
                .resolution(ResolutionPeriod.DAY)
                .duration(3, Duration.DAY)
                .build();

        assertNotNull(result);
        assertEquals(new LinkedHashSet<>(Arrays.asList(StatsEventType.OPENED.getValue(), StatsEventType.ACCEPTED.getValue(), StatsEventType.DELIVERED.getValue())), result.getEvent());
        assertEquals(ResolutionPeriod.DAY.getValue(), result.getResolution());
        assertEquals("3d", result.getDuration());
    }

}
