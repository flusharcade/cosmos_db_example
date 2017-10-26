package microsoft.cosmos_db_example.Services;

import io.reactivex.Observable;
import microsoft.cosmos_db_example.Constants.DBConstants;
import microsoft.cosmos_db_example.Models.TodoItem;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by mww121 on 26/10/17.
 */

public class CosmosService {
    // We are implementing against version 2.5 of the Open Weather Map web service.
    private static final String WEB_SERVICE_BASE_URL = "http://api.openweathermap.org/data/2.5";
    private static final String API_KEY = "insert your api key here";
    private final OpenWeatherMapWebService mWebService;

    public CosmosService() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Accept", "application/json");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(DBConstants.EndpointUrl)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mWebService = restAdapter.create(OpenWeatherMapWebService.class);
    }

    private interface OpenWeatherMapWebService {
        @GET("/weather?units=metric&apikey=" + API_KEY)
        Observable<CurrentWeatherDataEnvelope> fetchCurrentWeather(@Query("lon") double longitude,
                                                                   @Query("lat") double latitude);

//        @GET("/forecast/daily?units=metric&cnt=7&apikey=" + API_KEY)
//        Observable<WeatherForecastListDataEnvelope> fetchWeatherForecasts(
//                @Query("lon") double longitude, @Query("lat") double latitude);
    }

    public Observable<TodoItem> fetchCurrentWeather(final double longitude,
                                                    final double latitude) {
        return mWebService.fetchCurrentWeather(longitude, latitude)
                .flatMap(new Func1<CurrentWeatherDataEnvelope,
                        Observable<? extends CurrentWeatherDataEnvelope>>() {

                    // Error out if the request was not successful.
                    @Override
                    public Observable<? extends CurrentWeatherDataEnvelope> call(
                            final CurrentWeatherDataEnvelope data) {
                        return data.filterWebServiceErrors();
                    }

                }).map(new Func1<CurrentWeatherDataEnvelope, TodoItem>() {

                    // Parse the result and build a CurrentWeather object.
                    @Override
                    public CurrentWeather call(final CurrentWeatherDataEnvelope data) {
                        return new CurrentWeather(data.locationName, data.timestamp,
                                data.weather.get(0).description, data.main.temp,
                                data.main.temp_min, data.main.temp_max);
                    }
                });
    }

    /*public Observable<List<WeatherForecast>> fetchWeatherForecasts(final double longitude,
                                                                   final double latitude) {
        return mWebService.fetchWeatherForecasts(longitude, latitude)
                .flatMap(new Func1<WeatherForecastListDataEnvelope,
                        Observable<? extends WeatherForecastListDataEnvelope>>() {

                    // Error out if the request was not successful.
                    @Override
                    public Observable<? extends WeatherForecastListDataEnvelope> call(
                            final WeatherForecastListDataEnvelope listData) {
                        return listData.filterWebServiceErrors();
                    }

                }).map(new Func1<WeatherForecastListDataEnvelope, List<WeatherForecast>>() {

                    // Parse the result and build a list of WeatherForecast objects.
                    @Override
                    public List<WeatherForecast> call(final WeatherForecastListDataEnvelope listData) {
                        final ArrayList<WeatherForecast> weatherForecasts =
                                new ArrayList<>();

                        for (WeatherForecastListDataEnvelope.ForecastDataEnvelope data : listData.list) {
                            final WeatherForecast weatherForecast = new WeatherForecast(
                                    listData.city.name, data.timestamp, data.weather.get(0).description,
                                    data.temp.min, data.temp.max);
                            weatherForecasts.add(weatherForecast);
                        }

                        return weatherForecasts;
                    }
                });
    }*/
}