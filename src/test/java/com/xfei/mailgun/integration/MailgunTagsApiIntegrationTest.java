package com.xfei.mailgun.integration;

import com.xfei.mailgun.constants.IntegrationTestConstants;
import com.xfei.mailgun.constants.TestConstants;
import com.xfei.mailgun.api.v3.MailgunTagsApi;
import com.xfei.mailgun.client.MailgunClient;
import com.xfei.mailgun.enums.Duration;
import com.xfei.mailgun.enums.ResolutionPeriod;
import com.xfei.mailgun.enums.StatsEventType;
import com.xfei.mailgun.model.ResponseWithMessage;
import com.xfei.mailgun.model.StatisticsOptions;
import com.xfei.mailgun.model.stats.Stats;
import com.xfei.mailgun.model.tags.DeviceEventTypes;
import com.xfei.mailgun.model.tags.Devices;
import com.xfei.mailgun.model.tags.TagCountriesResponse;
import com.xfei.mailgun.model.tags.TagDevicesResponse;
import com.xfei.mailgun.model.tags.TagProvidersResponse;
import com.xfei.mailgun.model.tags.TagStatsResult;
import com.xfei.mailgun.model.tags.TagUpdateRequest;
import com.xfei.mailgun.model.tags.TagsItem;
import com.xfei.mailgun.model.tags.TagsResult;
import feign.FeignException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Use these tests as examples for a real implementation.")
class MailgunTagsApiIntegrationTest {

    private static final String TAG = "testing";

    private static MailgunTagsApi mailgunTagsApi;

    @BeforeAll
    static void beforeAll() {
        mailgunTagsApi = MailgunClient.config(IntegrationTestConstants.PRIVATE_API_KEY)
                .createApi(MailgunTagsApi.class);
    }

    @Test
    void getAllTagsSuccessTest() {
        TagsResult result = mailgunTagsApi.getAllTags(IntegrationTestConstants.MAIN_DOMAIN);

        assertNotNull(result.getPaging());
        List<TagsItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        TagsItem tagsItem = items.get(0);
        assertNotNull(tagsItem.getTag());
        assertNotNull(tagsItem.getDescription());
        assertNotNull(tagsItem.getFirstSeen());
        assertNotNull(tagsItem.getLastSeen());
    }

    @Test
    void getAllTagsLimitSuccessTest() {
        TagsResult result = mailgunTagsApi.getAllTags(IntegrationTestConstants.MAIN_DOMAIN, 2);

        assertNotNull(result.getPaging());
        List<TagsItem> items = result.getItems();
        assertTrue(CollectionUtils.isNotEmpty(items));
        assertEquals(2, items.size());
    }

    @Test
    void getTagSuccessTest() {
        TagsItem result = mailgunTagsApi.getTag(IntegrationTestConstants.MAIN_DOMAIN, TAG);

        assertNotNull(result.getTag());
        assertNotNull(result.getDescription());
        assertNotNull(result.getFirstSeen());
        assertNotNull(result.getLastSeen());
    }

    @Test
    void updateTagSuccessTest() {
        String newDescription = "New description";
        TagUpdateRequest tagUpdateRequest = TagUpdateRequest.builder()
                .description(newDescription)
                .build();

        ResponseWithMessage result = mailgunTagsApi.updateTag(IntegrationTestConstants.MAIN_DOMAIN, TAG, tagUpdateRequest);

        assertEquals("Tag updated", result.getMessage());

        TagsItem tag = mailgunTagsApi.getTag(IntegrationTestConstants.MAIN_DOMAIN, TAG);

        assertEquals(newDescription, tag.getDescription());
    }

    @Test
    void getTagStatisticsTimeRangeSuccessTest() {
        StatisticsOptions statisticsOptions = StatisticsOptions.builder()
                .event(Arrays.asList(StatsEventType.ACCEPTED, StatsEventType.DELIVERED))
                .start(TestConstants.ZONED_DATE_TIME_2020_4_5_GMT)
                .end(TestConstants.ZONED_DATE_TIME_2020_5_5_GMT)
                .build();

        TagStatsResult result = mailgunTagsApi.getTagStatistics(IntegrationTestConstants.MAIN_DOMAIN, TAG, statisticsOptions);

        assertNotNull(result.getTag());
        assertNotNull(result.getDescription());
        assertNotNull(result.getStart());
        assertNotNull(result.getEnd());
        assertNotNull(result.getResolution());
        List<Stats> statsList = result.getStats();
        assertTrue(CollectionUtils.isNotEmpty(statsList));
        Stats stats = statsList.get(0);
        assertNotNull(stats.getTime());
        assertNotNull(stats.getAccepted());
        assertNotNull(stats.getDelivered());
    }

    @Test
    void getTagStatisticsDurationSuccessTest() {
        StatisticsOptions statisticsOptions = StatisticsOptions.builder()
                .event(Arrays.asList(StatsEventType.ACCEPTED, StatsEventType.DELIVERED))
                .resolution(ResolutionPeriod.DAY)
                .duration(3, Duration.DAY)
                .build();

        TagStatsResult result = mailgunTagsApi.getTagStatistics(IntegrationTestConstants.MAIN_DOMAIN, TAG, statisticsOptions);

        assertNotNull(result.getTag());
        assertNotNull(result.getDescription());
        assertNotNull(result.getStart());
        assertNotNull(result.getEnd());
        assertNotNull(result.getResolution());
        List<Stats> statsList = result.getStats();
        assertTrue(CollectionUtils.isNotEmpty(statsList));
        Stats stats = statsList.get(0);
        assertNotNull(stats.getTime());
        assertNotNull(stats.getAccepted());
        assertNotNull(stats.getDelivered());
    }

    @Disabled("For safety")
    @Test
    void deleteTagSuccessTest() {
        String tagToDelete = "test_tag_11_02";
        ResponseWithMessage result = mailgunTagsApi.deleteTag(IntegrationTestConstants.MAIN_DOMAIN, tagToDelete);

        assertEquals("Tag deleted", result.getMessage());

        FeignException exception = assertThrows(FeignException.class, () ->
                mailgunTagsApi.getTag(IntegrationTestConstants.MAIN_DOMAIN, tagToDelete)
        );

        assertTrue(exception.getMessage().contains("template version not found"));
        assertEquals(404, exception.status());
    }

    @Test
    void listTagCountriesSuccessTest() {
        TagCountriesResponse result = mailgunTagsApi.listTagCountries(IntegrationTestConstants.MAIN_DOMAIN, TAG);

        assertEquals(TAG, result.getTag());
        assertTrue(MapUtils.isNotEmpty(result.getCountry()));
    }


    @Test
    void listTagProvidersSuccessTest() {
        TagProvidersResponse result = mailgunTagsApi.listTagProviders(IntegrationTestConstants.MAIN_DOMAIN, TAG);

        assertEquals(TAG, result.getTag());
        assertTrue(MapUtils.isNotEmpty(result.getProvider()));
    }

    @Test
    void listTagDevicesSuccessTest() {
        TagDevicesResponse result = mailgunTagsApi.listTagDevices(IntegrationTestConstants.MAIN_DOMAIN, TAG);

        assertEquals(TAG, result.getTag());
        Devices devices = result.getDevice();
        assertNotNull(devices);
        DeviceEventTypes desktop = devices.getDesktop();
        assertNotNull(desktop);
        assertNotNull(devices.getMobile());
        assertNotNull(devices.getTablet());
        assertNotNull(devices.getUnknown());
        assertNotNull(desktop.getClicked());
        assertNotNull(desktop.getComplained());
        assertNotNull(desktop.getOpened());
        assertNotNull(desktop.getUniqueClicked());
        assertNotNull(desktop.getUniqueOpened());
        assertNotNull(desktop.getUnsubscribed());
    }

}
