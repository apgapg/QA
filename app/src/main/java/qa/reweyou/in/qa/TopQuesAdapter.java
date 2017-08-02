package qa.reweyou.in.qa;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qa.reweyou.in.qa.model.TopQuestionModel;

/**
 * Created by master on 1/5/17.
 */

public class TopQuesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = TopQuesAdapter.class.getName();
    private final Context context;
    List<TopQuestionModel> messagelist;

    public TopQuesAdapter(Context context) {
        this.context = context;
        this.messagelist = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopQuesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_ques, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TopQuesHolder topQuesHolder = (TopQuesHolder) holder;

        // topQuesHolder.textcomment.setText(""+messagelist.get(position).get);
        topQuesHolder.textlike.setText(messagelist.get(position).getUpvotes() + " Like");
        topQuesHolder.textques.setText(messagelist.get(position).getQuestion());
        topQuesHolder.textcomment.setText(messagelist.get(position).getAnswers()+" Ans");
        topQuesHolder.texthashtag.setText("#"+messagelist.get(position).getHashtag());


    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<TopQuestionModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        notifyDataSetChanged();
    }


    private class TopQuesHolder extends RecyclerView.ViewHolder {

        private CardView cv;
        private TextView textques;
        private TextView textcomment;
        private TextView textlike;
        private TextView textnoviews,texthashtag;

        public TopQuesHolder(View inflate) {
            super(inflate);
            cv = inflate.findViewById(R.id.cv);
            textcomment = inflate.findViewById(R.id.textcomment);
            textlike = inflate.findViewById(R.id.textlike);
            textques = inflate.findViewById(R.id.textques);
            texthashtag = inflate.findViewById(R.id.texthashtag);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).showSecondPage(messagelist.get(getAdapterPosition()).getQueid(), messagelist.get(getAdapterPosition()).getQuestion());
                }
            });

        }
    }

}
