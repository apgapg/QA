package qa.reweyou.in.qa.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qa.reweyou.in.qa.MainActivity;
import qa.reweyou.in.qa.R;
import qa.reweyou.in.qa.VideoCapturetest;

/**
 * Created by master on 30/7/17.
 */

public class ReplyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_reply, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ReplyAdapter(getActivity()));

        view.findViewById(R.id.reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  getActivity().startActivity(new Intent(getActivity(), VideoCapturetest.class));

                ((MainActivity) getActivity()).shootvideo();
            }
        });
        return view;
    }
}
