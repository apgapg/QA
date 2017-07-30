package qa.reweyou.in.qa;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by master on 30/7/17.
 */

public class ApplicationClass extends Application{
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
