package com.xfei.millionverifier.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xfei.mailgun.api.MailgunApi;
import com.xfei.mailgun.form.FormEncoder;
import com.xfei.util.ConsoleLogger;
import static com.xfei.util.Constants.MILLION_VERIFIER_DEFAULT_BASE_URL_US_REGION;
import com.xfei.util.MailgunApiUtil;
import com.xfei.util.ObjectMapperUtil;
import feign.AsyncClient;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.querymap.FieldQueryMapEncoder;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Executors;

/**
 * MillionVerifier-Client
 *
 * @author yzq
 * @date 2023/03/24 17:23
 */
@UtilityClass
public class MillionVerifierClient {
    private static final ObjectMapper OBJECT_MAPPER = ObjectMapperUtil.getObjectMapper();
    private static final FormEncoder ENCODER = new FormEncoder(new JacksonEncoder(OBJECT_MAPPER));
    private static final JacksonDecoder DECODER = new JacksonDecoder(OBJECT_MAPPER);
    private static final FieldQueryMapEncoder QUERY_MAP_ENCODER = new FieldQueryMapEncoder();


    public MillionVerifierClientBuilder config(String baseUrl) {
        return new MillionVerifierClient.MillionVerifierClientBuilder(baseUrl);
    }

    public MillionVerifierClientBuilder config() {
        return new MillionVerifierClient.MillionVerifierClientBuilder();
    }

    public static class MillionVerifierClientBuilder {
        private Logger.Level logLevel = Logger.Level.FULL;
        private Retryer retryer = new Retryer.Default();
        private Logger logger = new ConsoleLogger();
        private ErrorDecoder errorDecoder = new ErrorDecoder.Default();
        private Request.Options options = new Request.Options();
        private AsyncClient<Object> client = new AsyncClient.Default<>(
                new Client.Default(null, null),
                Executors.newSingleThreadExecutor()
        );

        private String baseUrl = MILLION_VERIFIER_DEFAULT_BASE_URL_US_REGION;

        private MillionVerifierClientBuilder(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public MillionVerifierClientBuilder() {
        }

        public MillionVerifierClientBuilder client(AsyncClient<Object> client) {
            this.client = client;
            return this;
        }

        /**
         * <p>
         * You can override the default level of logging {@link com.xfei.millionverifier.client.MillionVerifierClient.MillionVerifierClientBuilder#logLevel}.
         * </p>
         *
         * @param logLevel {@link Logger.Level}
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        public MillionVerifierClientBuilder logLevel(Logger.Level logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        /**
         * <p>
         * You can override the default retryer {@link com.xfei.millionverifier.client.MillionVerifierClient.MillionVerifierClientBuilder#retryer}.
         * </p>
         *
         * @param retryer implementation of {@link Retryer}
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        public MillionVerifierClientBuilder retryer(Retryer retryer) {
            this.retryer = retryer;
            return this;
        }

        /**
         * <p>
         * You can override the default logger {@link com.xfei.millionverifier.client.MillionVerifierClient.MillionVerifierClientBuilder#logger}.
         * </p>
         *
         * @param logger implementation of {@link Logger}
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        public MillionVerifierClientBuilder logger(Logger logger) {
            this.logger = logger;
            return this;
        }

        /**
         * <p>
         * You can override the default error decoder {@link com.xfei.millionverifier.client.MillionVerifierClient.MillionVerifierClientBuilder#errorDecoder}.
         * </p>
         *
         * @param errorDecoder implementation of {@link ErrorDecoder}
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        public MillionVerifierClientBuilder errorDecoder(ErrorDecoder errorDecoder) {
            this.errorDecoder = errorDecoder;
            return this;
        }

        /**
         * <p>
         * You can override the default the per-request settings {@link com.xfei.millionverifier.client.MillionVerifierClient.MillionVerifierClientBuilder#options}.
         * </p>
         *
         * @param options {@link Request.Options}
         * @return Returns a reference to this object so that method calls can be chained together.
         */
        public MillionVerifierClientBuilder options(Request.Options options) {
            this.options = options;
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> T createApiWithAbsoluteUrl(Class<?> apiType) {
            return getFeignBuilder()
                    .target((Class<T>) apiType, baseUrl);
        }

        @SuppressWarnings("unchecked")
        public <T> T createApi(Class<?> apiType,String baseUrl) {
            return getFeignBuilder()
                    .target((Class<T>) apiType, baseUrl);
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
                    .options(options)
                    .requestInterceptor(new BasicAuthRequestInterceptor("", ""));
        }
    }
}
