package microsoft.cosmos_db_example.Services;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import microsoft.cosmos_db_example.Constants.DBConstants;

/**
 * Created by mww121 on 26/10/17.
 */

public class CosmosService extends BaseWebService {
    public static CosmosService WebService;
    public static Boolean idBased = true;

    public static synchronized CosmosService getInstance() {
        if (WebService == null) {
            WebService = new CosmosService();
        }
        return WebService;
    }

    private String authString;

    public CosmosService() {
        super();

    }

    // gets all databases
    public void getDatabases(IAsyncResponse delegate) {
        String date = createDate();

        authString = generateAuthToken(HttpMethod.GET.toString(), "dbs", "", date,
                DBConstants.PrimaryKey, "master", "1.0");

        Send(delegate, DBConstants.EndpointUrl,"dbs", HttpMethod.GET, null, authString, date);
    }

    // creates a database
    public void createDatabase(IAsyncResponse delegate, String databaseId) {
        String date = createDate();

        authString = generateAuthToken(HttpMethod.GET.toString(), "dbs", "", date,
                DBConstants.PrimaryKey, "master", "1.0");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", databaseId);

        Send(delegate, DBConstants.EndpointUrl,"dbs", HttpMethod.GET, params, authString, date);
    }

    // get a database by id
    public void getDatabase(IAsyncResponse delegate, String databaseId) {
        String date = createDate();

        String resourceLink = String.format("%s/%s", "dbs/", databaseId);
        String resourceId = idBased ? resourceLink : databaseId;

        authString = generateAuthToken(HttpMethod.GET.toString(), "dbs", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", databaseId);

        Send(delegate, DBConstants.EndpointUrl,resourceLink, HttpMethod.GET, params, authString, date);
    }

    // gets all collections
    public void getCollections(IAsyncResponse delegate, String databaseId) {
        String date = createDate();

        String resourceLink = String.format("dbs/%s/colls", databaseId);
        String resourceId = (idBased) ? resourceLink : databaseId.toLowerCase(Locale.ROOT);

        authString = generateAuthToken(HttpMethod.GET.toString(), "colls", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        Send(delegate, DBConstants.EndpointUrl, resourceLink, HttpMethod.GET, null, authString, date);
    }

    // get a collection by id
    public void getCollection(IAsyncResponse delegate, String databaseId, String collectionId) {
        String date = createDate();

        String resourceLink = String.format("dbs/%s/colls/%s", databaseId, collectionId);
        String resourceId = idBased ? resourceLink : collectionId.toLowerCase(Locale.ROOT);

        authString = generateAuthToken(HttpMethod.GET.toString(), "colls", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", databaseId);

        Send(delegate, DBConstants.EndpointUrl, resourceLink, HttpMethod.GET, params, authString, date);
    }

    // creates a collection in specific database
    public void createCollection(IAsyncResponse delegate, String databaseId, String collectionId) {
        String date = createDate();

        authString = generateAuthToken(HttpMethod.POST.toString(), "colls", "dbs/example", date,
                DBConstants.PrimaryKey, "master", "1.0");

        Send(delegate, DBConstants.EndpointUrl,"dbs/example", HttpMethod.POST, null, authString, date);
    }

    // retrieves all documents from a particular collection
    public void getDocumentsInCollection(IAsyncResponse delegate, String databaseId, String collectionId) {
        String date = createDate();

        String resourceLink = String.format("dbs/%s/colls/%s/docs", databaseId, collectionId);
        String resourceId = idBased ? resourceLink : collectionId.toLowerCase(Locale.ROOT);

        authString = generateAuthToken(HttpMethod.GET.toString(), "docs", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", databaseId);

        Send(delegate, DBConstants.EndpointUrl, resourceLink, HttpMethod.GET, params, authString, date);
    }

    // get a document by id
    public void getDocument(IAsyncResponse delegate, String databaseId, String collectionId, String documentId) {
        String date = createDate();

        String resourceLink = String.format("dbs/%s/colls/%s/docs/%s", databaseId, collectionId, documentId);
        String resourceId = idBased ? resourceLink : documentId.toLowerCase(Locale.ROOT);

        authString = generateAuthToken(HttpMethod.GET.toString(), "docs", resourceId, date,
                DBConstants.PrimaryKey, "master", "1.0");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", databaseId);

        Send(delegate, DBConstants.EndpointUrl,resourceLink, HttpMethod.GET, params, authString, date);
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

    /*public void checkProgramCode(IAsyncResponse delegate, String programCode) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("code", programCode);

        Send(delegate, "api/v1/auth/studycode", DBConstants.EndpointUrl, HttpMethod.POST, params, "");
    }

    public void Login(IAsyncResponse delegate, String email, String password, String groupId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("group_id", groupId);

        Send(delegate, "api/v1/auth/login", Environment.getUrlBase(), HttpMethod.POST, params, "");
    }

    public void ForgotPassword(IAsyncResponse delegate, String email) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);

        Send(delegate, "api/v1/auth/forgotpassword", Environment.getUrlBase(), HttpMethod.POST, params, "");
    }

    public void Register(IAsyncResponse delegate, String email, String password, String age, String gender, String groupId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("age", age);
        params.put("gender", gender);
        params.put("group_id", groupId);

        Send(delegate, "api/v1/auth/register", Environment.getUrlBase(), HttpMethod.POST, params, "");
    }*/
}