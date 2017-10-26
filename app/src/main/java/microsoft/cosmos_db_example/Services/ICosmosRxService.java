package microsoft.cosmos_db_example.Services;

import microsoft.cosmos_db_example.Contracts.DatabasesContract;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by mww121 on 27/10/17.
 */

public interface ICosmosRxService {
    @GET("/dbs")
    Observable<DatabasesContract> getDatabases();

    //@GET("/dbs/{databaseId}")
    //Observable<DatabaseContract> getDatabaseById(@Query("databaseId") String databaseId);
}