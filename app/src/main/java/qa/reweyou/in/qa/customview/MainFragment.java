package qa.reweyou.in.qa.customview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import qa.reweyou.in.qa.AskQuestion;
import qa.reweyou.in.qa.model.TopQuestionModel;
import qa.reweyou.in.qa.NewQuesAdapter;
import qa.reweyou.in.qa.R;
import qa.reweyou.in.qa.TopQuesAdapter;
import qa.reweyou.in.qa.classes.UserSessionManager;
import qa.reweyou.in.qa.utils.NetworkHandler;

/**
 * Created by master on 30/7/17.
 */

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getName();
    private Context mContext;
    private UserSessionManager userSessionManager;
    private Gson gson;
    private List<TopQuestionModel> list = new ArrayList<>();
    private List<TopQuestionModel> list2 = new ArrayList<>();
    private TopQuesAdapter topQuesAdapter;
    private RecyclerView recyclerViewtopques;
    private NewQuesAdapter newQuesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSessionManager = new UserSessionManager(mContext);
        gson = new Gson();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerViewtopques = view.findViewById(R.id.recyclerview);
        recyclerViewtopques.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topQuesAdapter = new TopQuesAdapter(mContext);
        recyclerViewtopques.setAdapter(topQuesAdapter);

        RecyclerView recyclerViewnewques = view.findViewById(R.id.recyclerview2);
        recyclerViewnewques.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        newQuesAdapter = new NewQuesAdapter(getActivity());
        recyclerViewnewques.setAdapter(newQuesAdapter);
        view.findViewById(R.id.ask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
mContext.startActivity(new Intent(mContext, AskQuestion.class));
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            mContext = (Activity) context;
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
            getData2();
        }
    }

    private void getData2() {
        list2.clear();

        AndroidNetworking.post("https://www.reweyou.in/interview/new_questions.php")
                .addBodyParameter("uid", userSessionManager.getUID())
                .addBodyParameter("authtoken", userSessionManager.getAuthToken())
                .setTag("fetchgroups")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (new NetworkHandler().isActivityAlive(TAG, (Activity) mContext, response)) {

                            try {
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject jsonObject = response.getJSONObject(i);
                                    TopQuestionModel topQuestionModel = gson.fromJson(jsonObject.toString(), TopQuestionModel.class);
                                    list2.add(topQuestionModel);
                                }

                                newQuesAdapter.add(list2);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: " + anError);
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
                        if (new NetworkHandler().isActivityAlive(TAG, (Activity) mContext, response)) {

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
