package microsoft.cosmos_db_example.Services;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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

    public void createDocumentCollection() {
        String date = createDate();

        authString = generateAuthToken(HttpMethod.POST.toString(), "colls", "dbs/example", date,
                DBConstants.PrimaryKey, "master", "1.0");

    }

    private String createDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Calendar cal = Calendar.getInstance();

        return dateFormat.format(cal).toString();
    }

    private String generateAuthToken(String verb, String resourceType, String resourceId, String date, String key, String keyType, String tokenVersion) {
        try
        {
            int flags = Base64.NO_WRAP | Base64.URL_SAFE;

            byte[] secretKey = Base64.decode(key, flags);
            SecretKeySpec signingKey = new SecretKeySpec(secretKey, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            // check values of verb, resourceType and resourceId
            if(verb != null && !verb.isEmpty()) {
                verb = "";
            }
            if(resourceType != null && !resourceType.isEmpty()) {
                resourceType = "";
            }
            if(resourceId != null && !resourceId.isEmpty()) {
                resourceId = "";
            }

            String payLoad = String.format("{0}\n{1}\n{2}\n{3}\n{4}\n",
                verb.toLowerCase(),
                resourceType.toLowerCase(),
                resourceId,
                date.toLowerCase(), "");

            byte[] hashPayLoad = mac.doFinal(Base64.decode(payLoad, flags));

            String signature = Base64.encodeToString(hashPayLoad, flags);

            return URLEncoder.encode(String.format("type={0}&ver={1}&sig={2}", keyType, tokenVersion, signature), "");

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