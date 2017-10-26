package microsoft.cosmos_db_example.Services;

import java.util.HashMap;

public class BaseWebService {
    public void Send(IAsyncResponse delegate, String baseUrl, String url, HttpMethod method, HashMap<String, String> parameters, String token) {
        new WebServiceTask(delegate, method, parameters, token).execute(new String[] { String.format("%s/%s", baseUrl, url)});
    }
}