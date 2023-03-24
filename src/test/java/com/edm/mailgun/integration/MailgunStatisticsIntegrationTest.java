package com.edm.mailgun.integration;

import com.edm.mailgun.api.v3.MailgunStatisticsApi;
import com.edm.mailgun.client.MailgunClient;
import com.edm.mailgun.constants.IntegrationTestConstants;
import com.edm.mailgun.enums.Duration;
import com.edm.mailgun.enums.ResolutionPeriod;
import com.edm.mailgun.enums.StatsEventType;
import com.edm.mailgun.model.StatisticsOptions;
import com.edm.mailgun.model.stats.Accepted;
import com.edm.mailgun.model.stats.Delivered;
import com.edm.mailgun.model.stats.Failed;
import com.edm.mailgun.model.stats.Permanent;
import com.edm.mailgun.model.stats.Stats;
import com.edm.mailgun.model.stats.StatsResult;
import com.edm.mailgun.model.stats.StatsTotalValueObject;
import com.edm.mailgun.model.stats.Temporary;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Use these tests as examples for a real implementation.")
class MailgunStatisticsIntegrationTest {

    private static MailgunStatisticsApi mailgunStatisticsApi;

    @BeforeAll
    static void beforeAll() {
        mailgunStatisticsApi = MailgunClient.config(IntegrationTestConstants.PRIVATE_API_KEY)
                .createApi(MailgunStatisticsApi.class);
    }

    @Test
    void getDomainStatsAllSuccessTest() {
        StatisticsOptions statsOptions = StatisticsOptions.builder()
                .event(Arrays.asList(StatsEventType.values()))
                .build();

        StatsResult result = mailgunStatisticsApi.getDomainStats(IntegrationTestConstants.MAIN_DOMAIN, statsOptions);

        assertNotNull(result.getDescription());
        assertNotNull(result.getStart());
        assertNotNull(result.getEnd());
        assertNotNull(result.getResolution());
        List<Stats> stats = result.getStats();
        assertTrue(CollectionUtils.isNotEmpty(stats));
        Stats stat = stats.get(0);
        assertNotNull(stat.getTime());
        Accepted accepted = stat.getAccepted();
        assertNotNull(accepted);
        assertNotNull(accepted.getIncoming());
        assertNotNull(accepted.getOutgoing());
        assertNotNull(accepted.getTotal());
        verifyDelivered(stat.getDelivered());
        verifyFailed(stat.getFailed());
        StatsTotalValueObject stored = stat.getStored();
        assertNotNull(stored);
        assertNotNull(stored.getTotal());
        StatsTotalValueObject opened = stat.getOpened();
        assertNotNull(opened);
        assertNotNull(opened.getTotal());
        StatsTotalValueObject clicked = stat.getClicked();
        assertNotNull(clicked);
        assertNotNull(clicked.getTotal());
        StatsTotalValueObject unsubscribed = stat.getUnsubscribed();
        assertNotNull(unsubscribed);
        assertNotNull(unsubscribed.getTotal());
        StatsTotalValueObject complained = stat.getComplained();
        assertNotNull(complained);
        assertNotNull(complained.getTotal());
    }

    @Test
    void getDomainStatsTwoSuccessTest() {
        StatisticsOptions statsOptions = StatisticsOptions.builder()
                .event(Arrays.asList(StatsEventType.ACCEPTED, StatsEventType.DELIVERED))
                .build();

        StatsResult result = mailgunStatisticsApi.getDomainStats(IntegrationTestConstants.MAIN_DOMAIN, statsOptions);

        assertNotNull(result.getDescription());
        assertNotNull(result.getStart());
        assertNotNull(result.getEnd());
        assertNotNull(result.getResolution());
        assertTrue(CollectionUtils.isNotEmpty(result.getStats()));
    }

    @Test
    void getDomainStatsFilterStartSuccessTest() {
        StatisticsOptions statsOptions = StatisticsOptions.builder()
                .start(ZonedDateTime.now().minusDays(3))
                .end(ZonedDateTime.now())
                .event(Arrays.asList(StatsEventType.ACCEPTED, StatsEventType.DELIVERED))
                .build();

        StatsResult result = mailgunStatisticsApi.getDomainStats(IntegrationTestConstants.MAIN_DOMAIN, statsOptions);

        assertNotNull(result.getDescription());
        assertNotNull(result.getStart());
        assertNotNull(result.getEnd());
        assertNotNull(result.getResolution());
        assertTrue(CollectionUtils.isNotEmpty(result.getStats()));
    }

    @Test
    void getDomainStatsFilterDurationSuccessTest() {
        StatisticsOptions statsOptions = StatisticsOptions.builder()
                .event(Arrays.asList(StatsEventType.ACCEPTED, StatsEventType.DELIVERED))
                .resolution(ResolutionPeriod.DAY)
                .duration(3, Duration.DAY)
                .build();

        StatsResult result = mailgunStatisticsApi.getDomainStats(IntegrationTestConstants.MAIN_DOMAIN, statsOptions);

        assertNotNull(result.getDescription());
        assertNotNull(result.getStart());
        assertNotNull(result.getEnd());
        assertNotNull(result.getResolution());
        assertTrue(CollectionUtils.isNotEmpty(result.getStats()));
    }

    private void verifyDelivered(Delivered delivered) {
        assertNotNull(delivered);
        assertNotNull(delivered.getSmtp());
        assertNotNull(delivered.getHttp());
        assertNotNull(delivered.getOptimized());
        assertNotNull(delivered.getTotal());
    }

    private void verifyFailed(Failed failed) {
        assertNotNull(failed);
        Permanent permanent = failed.getPermanent();
        assertNotNull(permanent);
        assertNotNull(permanent.getSuppressBounce());
        assertNotNull(permanent.getSuppressUnsubscribe());
        assertNotNull(permanent.getSuppressComplaint());
        assertNotNull(permanent.getBounce());
        assertNotNull(permanent.getDelayedBounce());
        assertNotNull(permanent.getWebhook());
        assertNotNull(permanent.getOptimized());
        assertNotNull(permanent.getTotal());
        Temporary temporary = failed.getTemporary();
        assertNotNull(temporary);
        assertNotNull(temporary.getEspblock());
        assertNotNull(temporary.getTotal());
    }

}
