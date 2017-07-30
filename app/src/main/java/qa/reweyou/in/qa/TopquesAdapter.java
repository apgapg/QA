package qa.reweyou.in.qa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by master on 1/5/17.
 */

public class TopquesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = TopquesAdapter.class.getName();
    private final Context context;
    List<String> messagelist;

    public TopquesAdapter(Context context) {
        this.context = context;
        this.messagelist = new ArrayList<>();
        messagelist.add("");
        messagelist.add("");
        messagelist.add("");
        messagelist.add("");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BackgroundImagesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_ques, parent, false));
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


        public BackgroundImagesHolder(View inflate) {
            super(inflate);


        }
    }

}
