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

public class NewQuesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = TopQuesAdapter.class.getName();
    private final Context context;
    List<TopQuestionModel> messagelist;

    public NewQuesAdapter(Context context) {
        this.context = context;
        this.messagelist = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewQuesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_ques, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewQuesHolder newQuesHolder = (NewQuesHolder) holder;
        newQuesHolder.textlike.setText(messagelist.get(position).getUpvotes() + " Like");
        newQuesHolder.textques.setText(messagelist.get(position).getQuestion());
        newQuesHolder.textcomment.setText(messagelist.get(position).getAnswers() + " Ans");
        newQuesHolder.texthashtag.setText("#" + messagelist.get(position).getHashtag());
        newQuesHolder.textuser.setText(messagelist.get(position).getUsername());


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


    private class NewQuesHolder extends RecyclerView.ViewHolder {

        private CardView cv;
        private TextView textques;
        private TextView textcomment;
        private TextView textlike;
        private TextView textnoviews, texthashtag, textuser;

        public NewQuesHolder(View inflate) {
            super(inflate);
            cv = inflate.findViewById(R.id.cv);
            textcomment = inflate.findViewById(R.id.textcomment);
            textlike = inflate.findViewById(R.id.textlike);
            textques = inflate.findViewById(R.id.textques);
            texthashtag = inflate.findViewById(R.id.texthashtag);
            textuser = inflate.findViewById(R.id.textuser);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).showSecondPage(messagelist.get(getAdapterPosition()).getQueid(), messagelist.get(getAdapterPosition()).getQuestion());
                }
            });


        }
    }

}
