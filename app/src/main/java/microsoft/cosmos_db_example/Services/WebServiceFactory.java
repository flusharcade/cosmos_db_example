package microsoft.cosmos_db_example.Services;

import java.util.HashMap;

import microsoft.cosmos_db_example.Constants.DBConstants;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by mww121 on 26/10/17.
 */

public class WebServiceFactory {

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @param endPoint REST endpoint url
     * @return retrofit service with defined endpoint
     */
    public static <T> T create(final Class<T> clazz, final String endPoint, final HttpMethod method,
                                              final HashMap<String, String> parameters, final String authString,
                                              final String date, final String query) {

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("x-ms-date", date);
                request.addHeader("x-ms-version", "2015-08-06");
                request.addHeader("x-ms-documentdb-isquery", query);
                request.addHeader("authorization", authString);
                request.addHeader("Accept", "application/json");
            }
        };

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(DBConstants.EndpointUrl)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        T service = restAdapter.create(clazz);

        return service;
    }
}