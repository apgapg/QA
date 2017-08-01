package qa.reweyou.in.qa.customview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import qa.reweyou.in.qa.R;
import qa.reweyou.in.qa.VideoDisplay;

/**
 * Created by master on 1/5/17.
 */

public class ReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ReplyAdapter.class.getName();
    private final Context context;
    List<String> messagelist;

    public ReplyAdapter(Context context) {
        this.context = context;
        this.messagelist = new ArrayList<>();
        messagelist.add("");
        messagelist.add("");
        messagelist.add("");
        messagelist.add("");
        messagelist.add("");
        messagelist.add("");
        messagelist.add("");
        messagelist.add("");
        messagelist.add("");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BackgroundImagesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BackgroundImagesHolder forumViewHolder = (BackgroundImagesHolder) holder;


    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<String> list) {
        messagelist.clear();
        messagelist.addAll(list);
        notifyDataSetChanged();
    }


    private class BackgroundImagesHolder extends RecyclerView.ViewHolder {

        private CardView cv;

        public BackgroundImagesHolder(View inflate) {
            super(inflate);
            cv = inflate.findViewById(R.id.cv);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, VideoDisplay.class));
                }
            });

        }
    }

}
