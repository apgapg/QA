package qa.reweyou.in.qa.classes;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

/**
 * Created by master on 3/8/17.
 */

public class RequestHelper {

    private static final String TAG = RequestHelper.class.getName();

    public static void incrementViewsRequest(String ansid, String uid, String authtoken) {
        AndroidNetworking.post("https://www.reweyou.in/interview/views.php")
                .addBodyParameter("uid", uid)
                .addBodyParameter("authtoken", authtoken)
                .addBodyParameter("ansid", ansid)
                .setTag("fetchgroups")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: " + anError);

                    }
                });
    }
}
