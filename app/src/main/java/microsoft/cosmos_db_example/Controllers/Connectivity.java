package microsoft.cosmos_db_example.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mww121 on 29/08/2016.
 */

public class Connectivity {
    private static Connectivity instance = null;

    public static Connectivity getInstance() {
        if(instance == null) {
            instance = new Connectivity();
        }

        return instance;
    }

    protected Connectivity() {

    }

    private Boolean _isConnected;

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean getIsConnected() {
        return _isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        _isConnected = isConnected;
    }
}
