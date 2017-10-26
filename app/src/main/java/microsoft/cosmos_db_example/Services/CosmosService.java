package microsoft.cosmos_db_example.Services;

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

    public CosmosService() { super(); }

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