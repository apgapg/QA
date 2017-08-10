package qa.reweyou.in.qa.classes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import qa.reweyou.in.qa.NewQuesAdapter;
import qa.reweyou.in.qa.R;
import qa.reweyou.in.qa.model.TopQuestionModel;

/**
 * Created by master on 30/7/17.
 */

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getName();
    private Context mContext;
    private UserSessionManager userSessionManager;
    private Gson gson;
    private List<TopQuestionModel> list = new ArrayList<>();
    private List<TopQuestionModel> list2 = new ArrayList<>();
    private NewQuesAdapter newQuesAdapter;
    private RecyclerView recyclerViewtopques;
    private EditText editText;
    private ImageView imgimage;
    private TextView txtusername, txtactive, txtask, txtanswer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSessionManager = new UserSessionManager(mContext);
        gson = new Gson();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        imgimage = view.findViewById(R.id.image);
        txtusername = view.findViewById(R.id.username);
        txtactive = view.findViewById(R.id.active);
        txtask = view.findViewById(R.id.ask);
        txtanswer = view.findViewById(R.id.answer);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            mContext = context;
        else throw new IllegalArgumentException("Context should be an instance of Activity");
    }

    @Override
    public void onDestroy() {
        mContext = null;
        super.onDestroy();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isAdded()) {
            getData();
        }
    }

    private void getData() {

        AndroidNetworking.post("https://www.reweyou.in/interview/identity.php")
                .addBodyParameter("uid", userSessionManager.getUID())
                .addBodyParameter("authtoken", userSessionManager.getAuthToken())
                .setTag("fetchgroups")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            // [{"username":"Ayush P Gupta","profileurl":"https:\/\/lh5.googleusercontent.com\/-Oeijd1cy4wc\/AAAAAAAAAAI\/AAAAAAAAALg\/VH7kNRnWQgo\/s96-c\/photo.jpg","questions":"0","answers":"0","oneline":"","last_active":"2017-08-03 01:48:32"}]
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String username = jsonObject.getString("username");
                            String profileurl = jsonObject.getString("profileurl");
                            String questions = jsonObject.getString("questions");
                            String answers = jsonObject.getString("answers");
                            String oneline = jsonObject.getString("oneline");
                            String lastactive = jsonObject.getString("last_active");

                            Glide.with(mContext).load(profileurl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgimage);
                            txtusername.setText(username);
                            txtactive.setText(lastactive.replace("about ", ""));
                            txtask.setText(questions);
                            txtanswer.setText(answers);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: " + anError);
                    }
                });
    }

}
