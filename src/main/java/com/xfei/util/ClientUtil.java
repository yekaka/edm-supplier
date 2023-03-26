package com.xfei.util;

import feign.form.FormData;
import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 * client-util
 *
 * @author yzq
 * @date 2023/03/26 14:04
 */
@UtilityClass
public class ClientUtil {

    public static FormData getFormData(String contentType, String fileName, String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        byte[] bytes = IOUtils.toByteArray(inputStream);
        return new FormData(contentType, fileName, bytes);
    }
}
