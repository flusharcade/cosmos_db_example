package microsoft.cosmos_db_example.Contracts;

import com.google.gson.annotations.SerializedName;

import org.apache.http.HttpException;

import rx.Observable;

/**
 * Created by mww121 on 26/10/17.
 */

public class BaseContract {
    @SerializedName("_rid")
    private String rid;

    public String getRid() { return this.rid; }

    public void setRid(String rid) { this.rid = rid; }

    @SerializedName("cod")
    private int httpCode;

    class Weather {
        public String description;
    }

    /**
     * The web service always returns a HTTP header code of 200 and communicates errors
     * through a 'cod' field in the JSON payload of the response body.
     */
    public Observable filterWebServiceErrors() {
        if (httpCode == 200) {
            return Observable.just(this);
        } else {
            return Observable.error(
                    new HttpException("There was a problem fetching the weather data."));
        }
    }
}
