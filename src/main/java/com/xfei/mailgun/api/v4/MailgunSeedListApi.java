package com.xfei.mailgun.api.v4;

import com.xfei.mailgun.enums.ApiVersion;
import com.xfei.mailgun.model.seedlist.SeedListItem;
import com.xfei.mailgun.model.seedlist.SeedListRequest;
import com.xfei.mailgun.model.seedlist.SeedListsAttributesResponse;
import com.xfei.mailgun.model.seedlist.SeedListsFiltersResponse;
import com.xfei.mailgun.model.seedlist.SeedListsPageRequest;
import com.xfei.mailgun.model.seedlist.SeedListsResponse;
import com.xfei.mailgun.model.seedlist.SingleSeedListResponse;
import com.xfei.mailgun.api.MailgunApi;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import feign.Response;

/**
 * <p>
 * A seed list is an object that provides the mailing list for your inbox placement test.
 * It also acts as a container for all the results of those tests and will aggregate the stats of all the tests.
 * </p>
 * <p>
 * When you create a seed list you will be provided a mailing list.
 * You may adjust this mailing list as you see fit, but you must send to the target_email otherwise a placement test will not be run.
 * </p>
 *
 * @see <a href="https://documentation.mailgun.com/en/latest/api-inbox-placement.html">Inbox Placement</a>
 */
public interface MailgunSeedListApi extends MailgunApi {

    static ApiVersion getApiVersion() {
        return ApiVersion.V_4;
    }

    /**
     * <p>
     * Generate a seed list.
     * </p>
     *
     * @param request {@link com.xfei.mailgun.model.seedlist.SeedListRequest}
     * @return {@link com.xfei.mailgun.model.seedlist.SeedListItem}
     */
    @Headers({"Content-Type: multipart/form-data", "Accept: application/json"})
    @RequestLine("POST /inbox/seedlists")
    SeedListItem generateSeedList(SeedListRequest request);

    /**
     * <p>
     * Generate a seed list.
     * </p>
     *
     * @param request {@link SeedListRequest}
     * @return {@link Response}
     */
    @Headers({"Content-Type: multipart/form-data", "Accept: application/json"})
    @RequestLine("POST /inbox/seedlists")
    Response generateSeedListFeignResponse(SeedListRequest request);

    /**
     * <p>
     * Update a seed list.
     * </p>
     *
     * @param targetEmail target email
     * @param request {@link SeedListRequest}
     * @return {@link SeedListItem}
     */
    @Headers({"Content-Type: multipart/form-data", "Accept: application/json"})
    @RequestLine("PUT /inbox/seedlists/{targetEmail}")
    SeedListItem updateSeedList(@Param("targetEmail") String targetEmail, SeedListRequest request);

    /**
     * <p>
     * Update a seed list.
     * </p>
     *
     * @param targetEmail target email
     * @param request {@link SeedListRequest}
     * @return {@link Response}
     */
    @Headers({"Content-Type: multipart/form-data", "Accept: application/json"})
    @RequestLine("PUT /inbox/seedlists/{targetEmail}")
    Response updateSeedListFeignResponse(@Param("targetEmail") String targetEmail, SeedListRequest request);

    /**
     * <p>
     * Get a list of all of your seed lists.
     * </p>
     *
     * @return {@link com.xfei.mailgun.model.seedlist.SeedListsResponse}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists")
    SeedListsResponse getAllSeedLists();

    /**
     * <p>
     * Get a list of all of your seed lists.
     * </p>
     *
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists")
    Response getAllSeedListsFeignResponse();

    /**
     * <p>
     * Get a list of all of your seed lists.
     * </p>
     *
     * @param filter {@link com.xfei.mailgun.model.seedlist.SeedListsPageRequest}
     * @return {@link SeedListsResponse}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists")
    SeedListsResponse getAllSeedLists(@QueryMap SeedListsPageRequest filter);

    /**
     * <p>
     * Get a list of all of your seed lists.
     * </p>
     *
     * @param filter {@link SeedListsPageRequest}
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists")
    Response getAllSeedListsFeignResponse(@QueryMap SeedListsPageRequest filter);

    /**
     * <p>
     * Get a single seed list.
     * </p>
     *
     * @param targetEmail target email
     * @return {@link com.xfei.mailgun.model.seedlist.SingleSeedListResponse}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists/{targetEmail}")
    SingleSeedListResponse getSeedList(@Param("targetEmail") String targetEmail);

    /**
     * <p>
     * Get a single seed list.
     * </p>
     *
     * @param targetEmail target email
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists/{targetEmail}")
    Response getSeedListFeignResponse(@Param("targetEmail") String targetEmail);

    /**
     * <p>
     * Get all iterable attributes of seed lists.
     * You can use this endpoint to find all attributes that are available for listing values of.
     * </p>
     *
     * @return {@link com.xfei.mailgun.model.seedlist.SeedListsAttributesResponse}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists/a")
    SeedListsAttributesResponse getSeedListsAttributes();

    /**
     * <p>
     * Get all iterable attributes of seed lists.
     * You can use this endpoint to find all attributes that are available for listing values of.
     * </p>
     *
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists/a")
    Response getSeedListsAttributesFeignResponse();

    /**
     * <p>
     * Get all values of a specific attribute of your seed lists.
     * </p>
     *
     * @param attribute specific attribute
     * @return {@link SeedListsAttributesResponse}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists/a/{attribute}")
    SeedListsAttributesResponse getSeedListsAttribute(@Param("attribute") String attribute);

    /**
     * <p>
     * Get all values of a specific attribute of your seed lists.
     * </p>
     *
     * @param attribute specific attribute
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists/a/{attribute}")
    Response getSeedListsAttributeFeignResponse(@Param("attribute") String attribute);

    /**
     * <p>
     * Get all available filters for seed lists.
     * </p>
     *
     * @return {@link com.xfei.mailgun.model.seedlist.SeedListsFiltersResponse}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists/_filters")
    SeedListsFiltersResponse getSeedListFilters();

    /**
     * <p>
     * Get all available filters for seed lists.
     * </p>
     *
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/seedlists/_filters")
    Response getSeedListFiltersFeignResponse();

    /**
     * <p>
     * Delete a seed list.
     * </p>
     *
     * @param targetEmail target email
     * @return {@link Response}
     */
    @Headers({"Accept: application/json"})
    @RequestLine("DELETE /inbox/seedlists/{targetEmail}")
    Response deleteSeedListFeignResponse(@Param("targetEmail") String targetEmail);

    /**
     * <p>
     * Get List results.
     * </p>
     *
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/results")
    Response getResultsFeignResponse();

    /**
     * <p>
     * Get Available Result Filters.
     * </p>
     *
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/results")
    Response getAvailableResultFiltersFeignResponse();

    /**
     * <p>
     * Get all iterable attributes of results.
     * </p>
     *
     * @return {@link SeedListsAttributesResponse}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/results/a")
    SeedListsAttributesResponse getResultsAttributes();

    /**
     * <p>
     * Get all iterable attributes of results.
     * </p>
     *
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/results/a")
    Response getResultsAttributesFeignResponse();

    /**
     * <p>
     * Get all values of a specific attribute of your results lists.
     * </p>
     *
     * @param attribute specific attribute
     * @return {@link SeedListsAttributesResponse}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/results/a/{attribute}")
    SeedListsAttributesResponse getResultsAttribute(@Param("attribute") String attribute);

    /**
     * <p>
     * Get all values of a specific attribute of your results lists.
     * </p>
     *
     * @param attribute specific attribute
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/results/a/{attribute}")
    Response getResultsAttributeFeignResponse(@Param("attribute") String attribute);

    /**
     * <p>
     * Get a specific result.
     * </p>
     *
     * @param rid unique identifier for a results
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("GET /inbox/results/{rid}")
    Response getSpecificResultFeignResponse(@Param("rid") String rid);

    /**
     * <p>
     * Delete a result.
     * </p>
     *
     * @param rid unique identifier for a results
     * @return {@link Response}
     */
    @Headers("Accept: application/json")
    @RequestLine("DELETE /inbox/results/{rid}")
    Response deleteResultFeignResponse(@Param("rid") String rid);

}
