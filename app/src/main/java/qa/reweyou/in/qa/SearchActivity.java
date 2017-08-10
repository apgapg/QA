package qa.reweyou.in.qa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import qa.reweyou.in.qa.classes.UserSessionManager;
import qa.reweyou.in.qa.model.TopQuestionModel;
import qa.reweyou.in.qa.utils.NetworkHandler;

public class SearchActivity extends AppCompatActivity {

    private List<TopQuestionModel> list = new ArrayList<>();
    private UserSessionManager userSessionManager;
    private String TAG = SearchActivity.class.getName();
    private Gson gson = new Gson();

    private TopQuesAdapter topQuesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        userSessionManager = new UserSessionManager(this);
        final EditText editText = findViewById(R.id.edittext);
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length() > 0) {

                }
            }
        });
    }

    private void getData() {
        list.clear();

        AndroidNetworking.post("https://www.reweyou.in/interview/top_questions.php")
                .addBodyParameter("uid", userSessionManager.getUID())
                .addBodyParameter("authtoken", userSessionManager.getAuthToken())
                .setTag("fetchgroups")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (new NetworkHandler().isActivityAlive(TAG, SearchActivity.this, response)) {

                            try {
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject jsonObject = response.getJSONObject(i);
                                    TopQuestionModel topQuestionModel = gson.fromJson(jsonObject.toString(), TopQuestionModel.class);
                                    list.add(topQuestionModel);
                                }

                                topQuesAdapter.add(list);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: " + anError);

                    }
                });
    }
}
