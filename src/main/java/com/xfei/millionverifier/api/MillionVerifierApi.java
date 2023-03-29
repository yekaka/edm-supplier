package com.xfei.millionverifier.api;

import com.xfei.millionverifier.model.MillionSingleResponse;
import com.xfei.millionverifier.model.MillionUploadRequest;
import com.xfei.millionverifier.model.MillionUploadResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

/**
 * MillionVerifier-对应的接口
 *
 * @author yyy
 * @date 2023/03/24 17:23
 */
@Headers({"User-Agent:curl/7.64.1"})
public interface MillionVerifierApi {

    @Headers("Content-Type: multipart/form-data")
    @RequestLine("POST /upload?key={apiKey}")
    MillionUploadResponse uploadFile(@Param("apiKey") String apiKey, MillionUploadRequest request);

    @RequestLine("GET /fileinfo?key={apiKey}&file_id={fileId}")
    MillionUploadResponse getFileInfo(@Param("apiKey") String apiKey, @Param("fileId") Long fileId);

    @Headers("Content-Type: multipart/form-data")
    @RequestLine("GET /download?key={apiKey}&file_id={fileId}&filter=all")
    Response downloadFileInfo(@Param("apiKey") String apiKey, @Param("fileId") Long fileId);

    @Headers({"Content-Type: multipart/form-data", "Accept: application/json"})
    @RequestLine("GET ?api={apiKey}&email={email}&timeout=10")
    MillionSingleResponse singleVerify(@Param("apiKey") String apiKey, @Param("email") String email);

}
