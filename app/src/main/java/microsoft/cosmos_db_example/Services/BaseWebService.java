package microsoft.cosmos_db_example.Services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class BaseWebService {
    public void Send(IAsyncResponse delegate, String baseUrl, String url, HttpMethod method,
                     HashMap<String, String> parameters, String authString, String date) {
        new WebServiceTask(delegate, method, parameters, authString, date).execute(new String[] { String.format("%s/%s", baseUrl, url)});
    }

    public String createDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return String.format("%s %s", formatter.format(new Date()), "GMT");
    }
}