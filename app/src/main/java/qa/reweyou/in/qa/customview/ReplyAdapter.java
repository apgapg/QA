package qa.reweyou.in.qa.customview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import qa.reweyou.in.qa.R;
import qa.reweyou.in.qa.VideoDisplay;
import qa.reweyou.in.qa.classes.RequestHelper;
import qa.reweyou.in.qa.classes.UserSessionManager;
import qa.reweyou.in.qa.model.ReplyAnswerModel;

/**
 * Created by master on 1/5/17.
 */

public class ReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ReplyAdapter.class.getName();
    private final Context mContext;
    private final UserSessionManager userSessionManager;
    List<ReplyAnswerModel> messagelist;

    public ReplyAdapter(Context mContext) {
        this.mContext = mContext;
        this.messagelist = new ArrayList<>();
        userSessionManager = new UserSessionManager(mContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReplyAnswerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReplyAnswerViewHolder replyAnswerViewHolder = (ReplyAnswerViewHolder) holder;
        Glide.with(mContext).load(messagelist.get(position).getThumbnail()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(replyAnswerViewHolder.image);
        replyAnswerViewHolder.views.setText(messagelist.get(position).getViews());

        replyAnswerViewHolder.reply.setText(messagelist.get(position).getComments());
        replyAnswerViewHolder.likes.setText(messagelist.get(position).getUpvotes());
        replyAnswerViewHolder.duration.setText(messagelist.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<ReplyAnswerModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        notifyDataSetChanged();
    }


    private class ReplyAnswerViewHolder extends RecyclerView.ViewHolder {

        private CardView cv;
        private ImageView image;
        private TextView views;
        private TextView reply, likes, duration;

        public ReplyAnswerViewHolder(View inflate) {
            super(inflate);
            cv = inflate.findViewById(R.id.cv);
            image = inflate.findViewById(R.id.image);
            views = inflate.findViewById(R.id.views);
            reply = inflate.findViewById(R.id.reply);
            likes = inflate.findViewById(R.id.likes);
            duration = inflate.findViewById(R.id.duration);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RequestHelper.incrementViewsRequest(messagelist.get(getAdapterPosition()).getAnsid(), userSessionManager.getUID(), userSessionManager.getAuthToken());

                    Intent i = new Intent(mContext, VideoDisplay.class);
                    i.putExtra("url", messagelist.get(getAdapterPosition()).getAnswer());
                    i.putExtra("ansid", messagelist.get(getAdapterPosition()).getAnsid());
                    i.putExtra("queid", messagelist.get(getAdapterPosition()).getQueid());
                    mContext.startActivity(i);


                }
            });

        }
    }

}
