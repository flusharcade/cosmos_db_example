package microsoft.cosmos_db_example.Controllers;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import microsoft.cosmos_db_example.Constants.DBConstants;
import microsoft.cosmos_db_example.Contracts.DatabasesContract;
import microsoft.cosmos_db_example.Delegates.CosmosDelegate;
import microsoft.cosmos_db_example.Services.CosmosRxService;
import microsoft.cosmos_db_example.Services.HttpMethod;
import microsoft.cosmos_db_example.Services.ICosmosRxService;
import microsoft.cosmos_db_example.Services.WebServiceFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mww121 on 26/10/17.
 */

public class CosmosRxController {
    private CosmosRxService _cosmosService;
    private CosmosDelegate _delegate;

    private static CosmosRxController instance = null;

    public static CosmosRxController getInstance(CosmosDelegate delegate) {
        if(instance == null) {
            instance = new CosmosRxController(delegate);
        }
        else {
            instance.updateDelegate(delegate);
        }

        return instance;
    }

    protected CosmosRxController(CosmosDelegate delegate) {
        _delegate = delegate;
        _cosmosService = CosmosRxService.getInstance();
    }

    protected void updateDelegate(CosmosDelegate delegate) {
        _delegate = delegate;
    }

    // dbs
    public void getDatabases() {
        _cosmosService.getDatabases();
    }

    public Observable<DatabasesContract> getDatabasesTestOne() {
        String date = createDate();
        String authString = generateAuthToken(HttpMethod.GET.toString(), "dbs", "", date,
                DBConstants.PrimaryKey, "master", "1.0");

        ICosmosRxService service = WebServiceFactory.create(ICosmosRxService.class, DBConstants.EndpointUrl,
                HttpMethod.GET, null, authString, date, null);

        return service.getDatabases()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

                /*.subscribe(new Subscriber<DatabasesContract>() {
                    @Override
                    public final void onCompleted() {
                        // do nothing
                        Log.e("onError", "Complete");
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e("onError", e.getMessage());
                    }

                    @Override
                    public final void onNext(DatabasesContract response) {
                        //mCardAdapter.addData(response);
                    }
                })*/
    }

    /*public void createDatabase(String databaseId) {
        _cosmosService.createDatabase(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId);
    }

    public void getDatabase(String databaseId) {
        _cosmosService.getDatabase(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId);
    }

    // colls
    public void getCollections(String databaseId) {
        _cosmosService.getCollections(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId);
    }

    public void createCollection(String databaseId, String collectionId) {
        _cosmosService.createCollection(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId);
    }

    public void getCollection(String databaseId, String collectionId) {
        _cosmosService.getCollection(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId);
    }

    // docs
    public void getDocuments(String databaseId, String collectionId) {
        _cosmosService.getDocumentsInCollection(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId);
    }

    public void createDocument(String databaseId, String collectionId, String documentId, HashMap<String, String> documentParams) {
        _cosmosService.createDocumentInCollection(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId, documentParams);
    }

    public void getDocument(String databaseId, String collectionId, String documentId) {
        _cosmosService.getDocumentInCollection(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId);
    }

    // attachments
    public void getAttachments(String databaseId, String collectionId, String attachmentId) {
        _cosmosService.getAttachmentsInDocument(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, attachmentId);
    }

    public void createAttachment(String databaseId, String collectionId, String documentId, String attachmentId,
                                 String attachmentContentType, String media) {
        _cosmosService.createAttachmentInDocument(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId, attachmentId, attachmentContentType, media);
    }

    public void getAttachment(String databaseId, String collectionId, String documentId, String attachmentId) {
        _cosmosService.getAttachmentInDocument(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        }, databaseId, collectionId, documentId, attachmentId);
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