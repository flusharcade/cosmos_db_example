package microsoft.cosmos_db_example.Services;

import microsoft.cosmos_db_example.Constants.DBConstants;
import retrofit.RestAdapter;

/**
 * Created by mww121 on 26/10/17.
 */

public class WebServiceFactory {

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @return retrofit service with defined endpoint
     */
    public static <T> T create(final Class<T> clazz) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(DBConstants.EndpointUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        T service = restAdapter.create(clazz);

        return service;
    }
}