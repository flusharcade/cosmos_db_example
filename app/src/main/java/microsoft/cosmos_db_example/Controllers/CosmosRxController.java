package microsoft.cosmos_db_example.Controllers;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import microsoft.cosmos_db_example.Constants.DBConstants;
import microsoft.cosmos_db_example.Contracts.DatabaseContract;
import microsoft.cosmos_db_example.Contracts.DatabasesContract;
import microsoft.cosmos_db_example.Delegates.CosmosDelegate;
import microsoft.cosmos_db_example.Services.CosmosRxService;
import microsoft.cosmos_db_example.Services.HttpMethod;
import microsoft.cosmos_db_example.Services.WebServiceFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mww121 on 26/10/17.
 */

public class CosmosRxController {
    private static CosmosRxController instance = null;
    public static Boolean idBased = true;

    public static CosmosRxController getInstance(CosmosDelegate delegate) {
        if(instance == null) {
            instance = new CosmosRxController();
        }

        return instance;
    }

    // dbs
    public Observable<DatabasesContract> getDatabases() {
        String date = createDate();
        String authString = generateAuthToken(HttpMethod.GET.toString(), "dbs", "", date,
                DBConstants.PrimaryKey, "master", "1.0");

        CosmosRxService service = WebServiceFactory.create(CosmosRxService.class);

        return service.getDatabases(date, "2015-08-06", authString)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<DatabaseContract> getDatabaseById(String databaseId) {
        String date = createDate();

        String resourceLink = String.format("%s/%s", "dbs", databaseId);
        String resourceId = idBased ? resourceLink : databaseId;

        String authString = generateAuthToken(HttpMethod.GET.toString(), "dbs", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        CosmosRxService service = WebServiceFactory.create(CosmosRxService.class);

        return service.getDatabaseById(databaseId, date, "2015-08-06", authString)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> createDatabase(String databaseId) {
        String date = createDate();
        String authString = generateAuthToken(HttpMethod.POST.toString(), "dbs", "", date,
                DBConstants.PrimaryKey, "master", "1.0");

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("id", databaseId);

        CosmosRxService service = WebServiceFactory.create(CosmosRxService.class);

        return service.createDatabase(params, date, "2015-08-06", authString)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // colls
    public Observable<Object> getCollections(String databaseId) {
        String date = createDate();

        String resourceLink = String.format("dbs/%s/colls", databaseId);
        String resourceId = (idBased) ? String.format("dbs/%s", databaseId) : databaseId.toLowerCase(Locale.ROOT);

        String authString = generateAuthToken(HttpMethod.GET.toString(), "colls", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        CosmosRxService service = WebServiceFactory.create(CosmosRxService.class);

        return service.getCollections(databaseId, date, "2015-08-06", authString)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> getCollectionById(String databaseId, String collectionId) {
        String date = createDate();

        String resourceLink = String.format("dbs/%s/colls/%s", databaseId, collectionId);
        String resourceId = idBased ? resourceLink : collectionId.toLowerCase(Locale.ROOT);

        String authString = generateAuthToken(HttpMethod.GET.toString(), "colls", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", databaseId);

        CosmosRxService service = WebServiceFactory.create(CosmosRxService.class);

        return service.getCollectionById(databaseId, collectionId, date, "2015-08-06", authString)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> createCollection(String databaseId, String collectionId) {
        String date = createDate();

        String resourceLink = String.format("dbs/%s/colls", databaseId);
        String resourceId = idBased ? String.format("dbs/%s", databaseId) : collectionId.toLowerCase(Locale.ROOT);

        String authString = generateAuthToken(HttpMethod.POST.toString(), "colls", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("id", collectionId);

        CosmosRxService service = WebServiceFactory.create(CosmosRxService.class);

        return service.createCollection(databaseId, params, date, "2015-08-06", authString)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // docs
    public Observable<Object> getDocuments(String databaseId, String collectionId) {
        String date = createDate();

        String resourceLink = String.format("dbs/%s/colls/%s/docs", databaseId, collectionId);
        String resourceId = idBased ? String.format("dbs/%s/colls/%s", databaseId, collectionId)
                : collectionId.toLowerCase(Locale.ROOT);

        String authString = generateAuthToken(HttpMethod.GET.toString(), "docs", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        CosmosRxService service = WebServiceFactory.create(CosmosRxService.class);

        return service.getDocuments(databaseId, collectionId, date, "2015-08-06", authString)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> getDocumentById(String databaseId, String collectionId, String documentId) {
        String date = createDate();

        String resourceLink = String.format("dbs/%s/colls/%s/docs/%s", databaseId, collectionId, documentId);
        String resourceId = idBased ? resourceLink : documentId.toLowerCase(Locale.ROOT);

        String authString = generateAuthToken(HttpMethod.GET.toString(), "docs", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        CosmosRxService service = WebServiceFactory.create(CosmosRxService.class);

        return service.getDocumentById(databaseId, collectionId, documentId, date, "2015-08-06", authString)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> createDocument(String databaseId, String collectionId, String documentId,
                                             HashMap<String, Object> documentParams) {
        String date = createDate();

        String resourceLink = String.format("dbs/%s/colls/%s/docs", databaseId, collectionId, documentId);
        String resourceId = idBased ? String.format("dbs/%s/colls/%s", databaseId, collectionId) : documentId.toLowerCase(Locale.ROOT);

        String authString = generateAuthToken(HttpMethod.POST.toString(), "docs", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("id", documentId);

        // for all document parameters
        for (Map.Entry<String, Object> entry : documentParams.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            params.put(key, value);
        }

        CosmosRxService service = WebServiceFactory.create(CosmosRxService.class);

        return service.createDocument(databaseId, collectionId, params, date, "2015-08-06", authString)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // docs
    /*public void getDocuments(String databaseId, String collectionId) {
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