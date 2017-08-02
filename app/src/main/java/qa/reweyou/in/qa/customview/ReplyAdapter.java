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
import qa.reweyou.in.qa.model.ReplyAnswerModel;

/**
 * Created by master on 1/5/17.
 */

public class ReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ReplyAdapter.class.getName();
    private final Context mContext;
    List<ReplyAnswerModel> messagelist;

    public ReplyAdapter(Context mContext) {
        this.mContext = mContext;
        this.messagelist = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReplyAnswerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReplyAnswerViewHolder replyAnswerViewHolder = (ReplyAnswerViewHolder) holder;
        Glide.with(mContext).load(messagelist.get(position).getThumbnail()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(replyAnswerViewHolder.image);
        if (messagelist.get(position).getViews().equals("0")) {
            replyAnswerViewHolder.views.setText("no views");
        } else replyAnswerViewHolder.views.setText(messagelist.get(position).getViews() + " views");

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

        public ReplyAnswerViewHolder(View inflate) {
            super(inflate);
            cv = inflate.findViewById(R.id.cv);
            image = inflate.findViewById(R.id.image);
            views = inflate.findViewById(R.id.views);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(mContext, VideoDisplay.class);
                    i.putExtra("url", messagelist.get(getAdapterPosition()).getAnswer());
                    mContext.startActivity(i);

                }
            });

        }
    }

}
