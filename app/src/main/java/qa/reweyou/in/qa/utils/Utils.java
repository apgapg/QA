package qa.reweyou.in.qa.utils;

import android.content.Context;

/**
 * Created by master on 1/8/17.
 */

public class Utils {
    public static final String URL_PREFFIX = "https://www.reweyou.in/interview/";
    public static final int REQ_CODE_VIDEO_UPLOAD = 2212;
    public static  String QUES_ID ;

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
