package qa.reweyou.in.qa.customview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import qa.reweyou.in.qa.MainActivity;
import qa.reweyou.in.qa.R;
import qa.reweyou.in.qa.classes.UserSessionManager;
import qa.reweyou.in.qa.model.ReplyAnswerModel;
import qa.reweyou.in.qa.utils.NetworkHandler;

/**
 * Created by master on 30/7/17.
 */

public class ReplyFragment extends Fragment {

    private static final String TAG = ReplyFragment.class.getName();
    private String quesid;
    private String question;
    private TextView questiontxtview;
    private ReplyAdapter replyAdapter;

    private List<ReplyAnswerModel> list = new ArrayList<>();
    private UserSessionManager userSessionManager;
    private Gson gson;
    private Context mContext;
    private View nonettext;
    private View noansyet;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSessionManager = new UserSessionManager(mContext);
        gson = new Gson();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_reply, container, false);
        questiontxtview = view.findViewById(R.id.ques);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        replyAdapter = new ReplyAdapter(getActivity());
        recyclerView.setAdapter(replyAdapter);

        noansyet = view.findViewById(R.id.noansyet);
        nonettext = view.findViewById(R.id.nonettext);
        nonettext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        view.findViewById(R.id.reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  getActivity().startActivity(new Intent(getActivity(), VideoCapturetest.class));

                ((MainActivity) getActivity()).shootvideo(quesid);

            }
        });
        return view;
    }

    public void showdata(String quesid, String question) {
        this.quesid = quesid;
        this.question = question;
        questiontxtview.setText(question);

        getData();
    }

    private void getData() {
        list.clear();
        nonettext.setVisibility(View.INVISIBLE);
        noansyet.setVisibility(View.INVISIBLE);

        AndroidNetworking.post("https://www.reweyou.in/interview/single_answer.php")
                .addBodyParameter("uid", userSessionManager.getUID())
                .addBodyParameter("authtoken", userSessionManager.getAuthToken())
                .addBodyParameter("queid", quesid)
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
                                    ReplyAnswerModel replyAnswerModel = gson.fromJson(jsonObject.toString(), ReplyAnswerModel.class);
                                    list.add(replyAnswerModel);
                                }

                                replyAdapter.add(list);

                                if (list.isEmpty()) {
                                    noansyet.setVisibility(View.VISIBLE);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: " + anError);
                        nonettext.setVisibility(View.VISIBLE);
                        replyAdapter.add(list);
                    }
                });

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
}
