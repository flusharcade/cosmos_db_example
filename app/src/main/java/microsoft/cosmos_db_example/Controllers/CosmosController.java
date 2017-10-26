package microsoft.cosmos_db_example.Controllers;

import microsoft.cosmos_db_example.Delegates.CosmosDelegate;
import microsoft.cosmos_db_example.Services.CosmosService;
import microsoft.cosmos_db_example.Services.IAsyncResponse;

/**
 * Created by mww121 on 26/10/17.
 */

public class CosmosController {
    private CosmosService _cosmosService;
    private CosmosDelegate _delegate;

    private static CosmosController instance = null;

    public static CosmosController getInstance(CosmosDelegate delegate) {
        if(instance == null) {
            instance = new CosmosController(delegate);
        }
        else {
            instance.updateDelegate(delegate);
        }

        return instance;
    }

    protected CosmosController(CosmosDelegate delegate) {
        _delegate = delegate;
        _cosmosService = CosmosService.getInstance();
    }

    protected void updateDelegate(CosmosDelegate delegate) {
        _delegate = delegate;
    }

    public void getDatabases() {
        _cosmosService.getDatabases(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        });
    }

    public void createDatabase() {
        _cosmosService.createDocumentCollection(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                String test = "";
            }
        });

        /*_cosmosService.checkProgramCode(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                ProgramCodeContract contract = (ProgramCodeContract) JSONParser.getInstance().deserialize(output, ProgramCodeContract.class);

                if (contract != null && contract.Success) {
                    // save group id
                    // Note that this will set the Questionnaire Screens list as well
                    if (contract.Group != null) {
                        ProgramCode code = new ProgramCode(contract.Group.Id, programCode, contract.Group.Name, contract.Group.Screens);
                        User.getInstance().setProgram(code);
                    }

                    _delegate.didValidateProgramCode();
                }
                else {
                    Helpers.as(ErrorDelegate.class, _delegate).didError(contract);
                }
            }
        }, programCode);*/
    }

    /*public void login(String email, String password, String groudId)
    {
        _authWebService.Login(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                LoginContract contract = (LoginContract) JSONParser.getInstance().deserialize(output, LoginContract.class);

                if (contract != null && contract.User != null) {

                    // Set properties on the apps model object
                    User.getInstance().setUserId(contract.User.Id);
                    User.getInstance().setSessionToken( contract.Token);

                    _delegate.didLogin();
                }
                else {
                    Helpers.as(ErrorDelegate.class, _delegate).didError(contract);
                }
            }
        }, email, password, groudId);
    }

    public void registerUser(String email, String password, String age, String gender, String groudId)
    {
        _authWebService.Register(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                RegisterContract contract = (RegisterContract) JSONParser.getInstance().deserialize(output, RegisterContract.class);

                if (contract != null && contract.Success) {

                    // ? What about user ID etc
                    _delegate.didCreateAccount();
                }
                else {
                    Helpers.as(ErrorDelegate.class, _delegate).didError(contract);
                }
            }
        }, email, password, age, gender, groudId);
    }

    public void forgotPassword(String email) {
        _authWebService.ForgotPassword(new IAsyncResponse() {
            @Override
            public void processFinish(String output) {
                ForgotPasswordContract contract = (ForgotPasswordContract) JSONParser.getInstance().deserialize(output, ForgotPasswordContract.class);

                if (contract != null && contract.EmailSent) {
                    _delegate.didForgetPassword();
                }
                else {
                    Helpers.as(ErrorDelegate.class, _delegate).didError(contract);
                }
            }
        }, email);
    }*/
}