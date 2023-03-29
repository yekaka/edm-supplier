package com.xfei.sendcloud.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xfei.mailgun.form.FormEncoder;
import com.xfei.util.ConsoleLogger;
import static com.xfei.util.Constants.SEND_CLOUD_DEFAULT_BASE_URL_US_REGION;
import com.xfei.util.ObjectMapperUtil;
import feign.AsyncClient;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.querymap.FieldQueryMapEncoder;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Executors;

/**
 * sendCloud-client
 *
 * @author yyy
 * @date 2023/03/24 17:23
 */
@UtilityClass
public class SendCloudClient {

    private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtil.getObjectMapper();
    private static final FormEncoder ENCODER = new FormEncoder(new JacksonEncoder(OBJECT_MAPPER));
    private static final JacksonDecoder DECODER = new JacksonDecoder(OBJECT_MAPPER);
    private static final FieldQueryMapEncoder QUERY_MAP_ENCODER = new FieldQueryMapEncoder();


    public SendCloudClientBuilder config(String url) {
        return new SendCloudClientBuilder(url);
    }

    public SendCloudClientBuilder config() {
        return new SendCloudClientBuilder();
    }

    public static class SendCloudClientBuilder {
        private Logger.Level logLevel = Logger.Level.FULL;
        private Retryer retryer = new Retryer.Default();
        private Logger logger = new ConsoleLogger();
        private ErrorDecoder errorDecoder = new ErrorDecoder.Default();
        private Request.Options options = new Request.Options();
        private AsyncClient<Object> client = new AsyncClient.Default<>(
                new Client.Default(null, null),
                Executors.newSingleThreadExecutor()
        );

        private String baseUrl = SEND_CLOUD_DEFAULT_BASE_URL_US_REGION;

        private SendCloudClientBuilder(String url) {
            this.baseUrl = url;
        }

        public SendCloudClientBuilder() {
        }

        public SendCloudClientBuilder client(AsyncClient<Object> client) {
            this.client = client;
            return this;
        }

        /**
         * <p>
         * You can override the default level of logging {@link com.xfei.sendcloud.client.SendCloudClient.SendCloudClientBuilder#logLevel}.
         * </p>
         *
         * @param logLevel {@link Logger.Level}
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        public SendCloudClientBuilder logLevel(Logger.Level logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        /**
         * <p>
         * You can override the default retryer {@link com.xfei.sendcloud.client.SendCloudClient.SendCloudClientBuilder#retryer}.
         * </p>
         *
         * @param retryer implementation of {@link Retryer}
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        public SendCloudClientBuilder retryer(Retryer retryer) {
            this.retryer = retryer;
            return this;
        }

        /**
         * <p>
         * You can override the default logger {@link com.xfei.sendcloud.client.SendCloudClient.SendCloudClientBuilder#logger}.
         * </p>
         *
         * @param logger implementation of {@link Logger}
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        public SendCloudClientBuilder logger(Logger logger) {
            this.logger = logger;
            return this;
        }

        /**
         * <p>
         * You can override the default error decoder {@link com.xfei.sendcloud.client.SendCloudClient.SendCloudClientBuilder#errorDecoder}.
         * </p>
         *
         * @param errorDecoder implementation of {@link ErrorDecoder}
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        public SendCloudClientBuilder errorDecoder(ErrorDecoder errorDecoder) {
            this.errorDecoder = errorDecoder;
            return this;
        }

        /**
         * <p>
         * You can override the default the per-request settings {@link com.xfei.sendcloud.client.SendCloudClient.SendCloudClientBuilder#options}.
         * </p>
         *
         * @param options {@link Request.Options}
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        public SendCloudClientBuilder options(Request.Options options) {
            this.options = options;
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> T createApiWithAbsoluteUrl(Class<?> apiType) {
            return getFeignBuilder()
                    .target((Class<T>) apiType, baseUrl);
        }

        @SuppressWarnings("unchecked")
        public <T> T createApi(Class<?> apiType,String url) {
            return getFeignBuilder()
                    .target((Class<T>) apiType, url);
        }

        private Feign.Builder getFeignBuilder() {
            return Feign.builder()
                    .logLevel(logLevel)
                    .retryer(retryer)
                    .logger(logger)
                    .encoder(ENCODER)
                    .decoder(DECODER)
                    .queryMapEncoder(QUERY_MAP_ENCODER)
                    .errorDecoder(errorDecoder)
                    .options(options);
        }
    }
}
