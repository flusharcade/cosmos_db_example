package microsoft.cosmos_db_example.Controllers;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;

public class App extends Application {

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;

    private static Application sApplication;

    private static final String TAG = "AppDelegate";
    private static final String KEY_APP_CRASHED = "KEY_APP_CRASHED";
    private static boolean isRestartedFromCrash = false;
    public static boolean getIsRestartedFromCrash() { return isRestartedFromCrash; }
    public static void clearIsRestartedFromCrash(){ isRestartedFromCrash = false; }

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public static String appVersion() {
        try {
            PackageInfo pInfo = sApplication.getPackageManager().getPackageInfo(sApplication.getPackageName(), 0);
            return pInfo.versionName;
        } catch(Exception e) {

        }
        return "1.6.4";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable exception) {
                // Save the fact we crashed out.
                getSharedPreferences( TAG , Context.MODE_PRIVATE ).edit()
                        .putBoolean( KEY_APP_CRASHED, true ).apply();
                // Chain default exception handler.
                if ( defaultHandler != null ) {
                    defaultHandler.uncaughtException( thread, exception );
                }
            }
        } );

        boolean bRestartAfterCrash = getSharedPreferences( TAG , Context.MODE_PRIVATE )
                .getBoolean( KEY_APP_CRASHED, false );
        if ( bRestartAfterCrash ) {
            // Clear crash flag.
            isRestartedFromCrash = true;

            getSharedPreferences( TAG , Context.MODE_PRIVATE ).edit()
                    .putBoolean( KEY_APP_CRASHED, false ).apply();
        }

    }
}