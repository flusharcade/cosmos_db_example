package microsoft.cosmos_db_example.Services;

import android.util.Base64;

import com.squareup.okhttp.RequestBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import microsoft.cosmos_db_example.Constants.DBConstants;
import microsoft.cosmos_db_example.Contracts.DatabaseContract;
import microsoft.cosmos_db_example.Contracts.DatabasesContract;
import microsoft.cosmos_db_example.Models.Database;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by mww121 on 26/10/17.
 */

public class CosmosRxService {
    private HttpMethod _method;

    private HashMap<String, String> _parameters;

    private RequestBody _body;

    private String _date;

    private String _authString;

    private String _query;

    public static CosmosRxService WebService;

    private ICosmosRxService mWebService;

    public static synchronized CosmosRxService getInstance() {
        if (WebService == null) {
            WebService = new CosmosRxService();
        }
        return WebService;
    }

    private String authString;

    public CosmosRxService() {
        super();

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("x-ms-date", _date);
                request.addHeader("x-ms-version", "2015-08-06");
                request.addHeader("x-ms-documentdb-isquery", _query);
                request.addHeader("authorization", _authString);
                request.addHeader("Accept", "application/json");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(DBConstants.EndpointUrl)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mWebService = restAdapter.create(ICosmosRxService.class);
    }

    public Observable<List<Database>> getDatabases() {
        _date = createDate();
        _authString = generateAuthToken(HttpMethod.GET.toString(), "dbs", "", _date,
                DBConstants.PrimaryKey, "master", "1.0");

        return mWebService.getDatabases()
                .flatMap(new Func1<DatabasesContract, Observable<? extends DatabasesContract>>() {
                    // Error out if the request was not successful.
                    @Override
                    public Observable<? extends DatabasesContract> call(
                            final DatabasesContract listData) {
                        return listData.filterWebServiceErrors();
                    }

                }).map(new Func1<DatabasesContract, List<Database>>() {

                    // Parse the result and build a list of WeatherForecast objects.
                    @Override
                    public List<Database> call(final DatabasesContract databasesContract) {
                        final ArrayList<Database> databases =
                                new ArrayList<>();

                        for (DatabaseContract data : databasesContract.getDatabases()) {
                            final Database database = new Database(data.getId(), data.getSelf(),
                                    data.getEtag(), data.getColls(),
                                    data.getUsers(), data.getTs());
                            databases.add(database);
                        }

                        return databases;
                    }
                });
    }

    /*public Observable<DatabaseContract> getDatabaseById(String databaseId) {
        return mWebService.getDatabaseById(databaseId)
                .flatMap(new Func1<DatabaseContract, Observable<? extends DatabaseContract>>() {

                    // Error out if the request was not successful.
                    @Override
                    public Observable<? extends DatabaseContract> call(
                            final DatabaseContract data) {
                        return data.filterWebServiceErrors();
                    }

                }).map(new Func1<DatabaseContract, Database>() {

                    @Override
                    public Database call(DatabaseContract data) {
                        return new Database(data.getId(), data.getSelf(),
                                data.getEtag(), data.getColls(),
                                data.getUsers(), data.getTs());
                    }
                });
    }*/

    public String createDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return String.format("%s %s", formatter.format(new Date()), "GMT");
    }

    private String generateAuthToken(String verb, String resourceType, String resourceId, String date, String key, String keyType, String tokenVersion) {
        try
        {
            byte[] decodedKey = org.apache.commons.codec.binary.Base64.decodeBase64(key.getBytes());

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(decodedKey, "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String payload = String.format("%s\n%s\n%s\n%s\n%s\n",
                    verb.toLowerCase(Locale.ROOT),
                    resourceType.toLowerCase(Locale.ROOT),
                    resourceId,
                    date.toLowerCase(Locale.ROOT), "");

            byte[] hashPayLoad = sha256_HMAC.doFinal(payload.getBytes("UTF-8"));

            String signature = Base64.encodeToString(hashPayLoad, Base64.DEFAULT).replace("\n", "");

            return URLEncoder.encode(String.format("type=%s&ver=%s&sig=%s", keyType, tokenVersion, signature), "UTF-8");

        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}