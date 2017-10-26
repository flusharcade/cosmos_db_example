package microsoft.cosmos_db_example.Services;

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import microsoft.cosmos_db_example.Controllers.Connectivity;

/**
 * Created by mww121 on 17/08/2016.
 */
public class WebServiceTask extends AsyncTask<String, Void, String>
{
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public IAsyncResponse _delegate = null;

    private HttpMethod _method;

    private HashMap<String, Object> _parameters;

    private RequestBody _body;

    private String _date;

    private String _authString;

    private String _query;

    WebServiceTask(IAsyncResponse delegate, HttpMethod method, HashMap<String, Object> parameters,
                   String authString, String date, String query) {
        _delegate = delegate;
        _method = method;
        _parameters = parameters;
        _date = date;
        _authString = authString;
        _query = query;

        _body = RequestBody.create(JSON, "");
    }

    @Override
    protected String doInBackground(String... urls) {
        // check first if we are still con
        if (Connectivity.getInstance().isNetworkAvailable()) {
            // we use the OkHttp library from https://github.com/square/okhttp
            OkHttpClient client = new OkHttpClient();

            Request request;
            Response response;

            JSONObject paramsJson;

            if (_parameters != null) {
                try {
                    paramsJson = new JSONObject(_parameters);
                    _body = RequestBody.create(JSON, paramsJson.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            switch (_method) {
                case GET:
                    request = new Request.Builder()
                            .addHeader("x-ms-date", _date)
                            .addHeader("x-ms-version", "2015-08-06")
                            .addHeader("authorization", _authString)
                            .addHeader("Accept", "application/json")
                            .url(urls[0])
                            .get()
                            .build();
                    break;
                case POST:
                    request = new Request.Builder()
                            .addHeader("x-ms-date", _date)
                            .addHeader("x-ms-version", "2015-08-06")
                            .addHeader("authorization", _authString)
                            .addHeader("Accept", "application/json")
                            .url(urls[0])
                            .post(_body)
                            .build();

                    if (_query != null && !_query.isEmpty()) {
                        request = new Request.Builder()
                                .addHeader("x-ms-date", _date)
                                .addHeader("x-ms-version", "2015-08-06")
                                .addHeader("x-ms-documentdb-isquery", _query)
                                .addHeader("authorization", _authString)
                                .addHeader("Accept", "application/json")
                                .url(urls[0])
                                .post(_body)
                                .build();
                    }
                    break;
                default:
                    request = new Request.Builder()
                            .url(urls[0])
                            .build();
                    break;
            }


            try {
                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    //Logger.getInstance().Log("response error: " + response.body().string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        _delegate.processFinish(result);
    }
}