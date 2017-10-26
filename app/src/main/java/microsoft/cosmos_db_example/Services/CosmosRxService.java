package microsoft.cosmos_db_example.Services;

import java.util.HashMap;

import microsoft.cosmos_db_example.Contracts.DatabaseContract;
import microsoft.cosmos_db_example.Contracts.DatabasesContract;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by mww121 on 27/10/17.
 */

public interface CosmosRxService {
    @GET("/dbs")
    Observable<DatabasesContract> getDatabases(@Header("x-ms-date") String date, @Header("x-ms-version") String version,
                                               @Header("authorization") String authString);

    @GET("/dbs/{dbid}")
    Observable<DatabaseContract> getDatabaseById(@Path("dbid") String databaseId, @Header("x-ms-date") String date,
                                                 @Header("x-ms-version") String version, @Header("authorization") String authString);

    @POST("/dbs")
    Observable<Object> createDatabase(@Body HashMap<String, Object> body, @Header("x-ms-date") String date,
                                                @Header("x-ms-version") String version, @Header("authorization") String authString);

    @GET("/dbs/{dbid}/colls")
    Observable<Object> getCollections(@Path("dbid") String databaseId, @Header("x-ms-date") String date,
                                               @Header("x-ms-version") String version, @Header("authorization") String authString);

    @GET("/dbs/{dbid}/colls/{collid}")
    Observable<Object> getCollectionById(@Path("dbid") String databaseId, @Path("collid") String collectionId,
                                                   @Header("x-ms-date") String date, @Header("x-ms-version") String version,
                                                   @Header("authorization") String authString);

    @POST("/dbs/{dbid}/colls")
    Observable<Object> createCollection(@Path("dbid") String databaseId, @Body HashMap<String, Object> body,
                                      @Header("x-ms-date") String date, @Header("x-ms-version") String version,
                                      @Header("authorization") String authString);

    @GET("/dbs/{dbid}/colls/{collid}/docs")
    Observable<Object> getDocuments(@Path("dbid") String databaseId, @Path("collid") String collectionId,
                                    @Header("x-ms-date") String date, @Header("x-ms-version") String version,
                                    @Header("authorization") String authString);

    @GET("/dbs/{dbid}/colls/{collid}/docs/{docid}")
    Observable<Object> getDocumentById(@Path("dbid") String databaseId, @Path("collid") String collectionId,
                                       @Path("docid") String documentId, @Header("x-ms-date") String date,
                                       @Header("x-ms-version") String version, @Header("authorization") String authString);

    @POST("/dbs/{dbid}/colls/{collid}/docs")
    Observable<Object> createDocument(@Path("dbid") String databaseId, @Path("collid") String collectionId,
                                      @Body HashMap<String, Object> body, @Header("x-ms-date") String date,
                                      @Header("x-ms-version") String version, @Header("authorization") String authString);

    @GET("/dbs/{dbid}/colls/{collid}/docs/{docid}/attachments")
    Observable<Object> getAttachments(@Path("dbid") String databaseId, @Path("collid") String collectionId,
                                      @Path("docid") String documentId, @Header("x-ms-date") String date,
                                    @Header("x-ms-version") String version, @Header("authorization") String authString);

    @GET("/dbs/{dbid}/colls/{collid}/docs/{docid}/attachments/{attachid}")
    Observable<Object> getAttachmentById(@Path("dbid") String databaseId, @Path("collid") String collectionId,
                                         @Path("docid") String documentId, @Path("attachid") String attachmentId,
                                       @Header("x-ms-date") String date, @Header("x-ms-version") String version,
                                       @Header("authorization") String authString);

    @POST("/dbs/{dbid}/colls/{collid}/docs/{docid}/attachments")
    Observable<Object> createAttachment(@Path("dbid") String databaseId, @Path("collid") String collectionId,
                                        @Path("docid") String documentId, @Body HashMap<String, Object> body,
                                        @Header("x-ms-date") String date, @Header("x-ms-version") String version,
                                        @Header("authorization") String authString);
}