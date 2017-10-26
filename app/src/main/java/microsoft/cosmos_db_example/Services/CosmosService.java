package microsoft.cosmos_db_example.Services;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import microsoft.cosmos_db_example.Constants.DBConstants;

/**
 * Created by mww121 on 26/10/17.
 */

public class CosmosService extends BaseWebService {
    public static CosmosService WebService;

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

    public void createDatabase() {

    }

    public void getDatabases(IAsyncResponse delegate) {
        String date = createDate();

        authString = generateAuthToken(HttpMethod.GET.toString(), "dbs", "", date,
                DBConstants.PrimaryKey, "master", "1.0");

        Send(delegate, DBConstants.EndpointUrl,"dbs", HttpMethod.GET, null, authString, date);
    }

    public void createDocumentCollection(IAsyncResponse delegate) {
        String date = "Thu, 26 Oct 2017 08:36:40 GMT";

        authString = generateAuthToken(HttpMethod.POST.toString(), "dbs", "dbs/example", date,
                DBConstants.PrimaryKey, "master", "1.0");

        Send(delegate, DBConstants.EndpointUrl,"dbs/example", HttpMethod.POST, null, authString, date);
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