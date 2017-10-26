package microsoft.cosmos_db_example.Services;

import io.reactivex.Observable;
import microsoft.cosmos_db_example.Models.TodoItem;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by mww121 on 26/10/17.
 */

public interface ICosmosDbService {
    //String SERVICE_ENDPOINT = "https://api.github.com";

    @GET("/users/{login}")
    Observable<TodoItem> getTodoItems(@Path("login") String login);
}