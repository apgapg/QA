package qa.reweyou.in.qa.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import qa.reweyou.in.qa.CommentActivity;
import qa.reweyou.in.qa.R;
import qa.reweyou.in.qa.model.CommentModel;
import qa.reweyou.in.qa.utils.Utils;

/**
 * Created by master on 1/5/17.
 */

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEWTYPE_COMMENT = 1;
    private static final int VIEWTYPE_REPLY = 2;
    private static final String TAG = CommentsAdapter.class.getName();
    private final Context mContext;
    private final UserSessionManager userSessionManager;
    List<Object> messagelist;
    private String threadId;


    public CommentsAdapter(Context context) {
        this.mContext = context;
        this.messagelist = new ArrayList<>();
        userSessionManager = new UserSessionManager(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_COMMENT)
            return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_main, parent, false));
        else return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentModel commentModel = (CommentModel) messagelist.get(position);
        CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
        commentViewHolder.username.setText(commentModel.getUsername());


        commentViewHolder.comment.setText(commentModel.getComment());


        commentViewHolder.time.setText(commentModel.getTimestamp().replace("about ", ""));

        if (((CommentModel) messagelist.get(position)).getUid().equals(userSessionManager.getUID()))
            commentViewHolder.edit.setVisibility(View.VISIBLE);
        else commentViewHolder.edit.setVisibility(View.INVISIBLE);
        Glide.with(mContext).load(((CommentModel) messagelist.get(position)).getImageurl()).error(R.drawable.download).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(commentViewHolder.image);
        commentViewHolder.userlevel.setText(((CommentModel) messagelist.get(position)).getBadge());
        commentViewHolder.likenumber.setText(((CommentModel) messagelist.get(position)).getUpvotes());
        if (((CommentModel) messagelist.get(position)).getStatus().equals("true")) {
            commentViewHolder.like.setImageResource(R.drawable.ic_heart_like);
        } else if (((CommentModel) messagelist.get(position)).getStatus().equals("false")) {

            commentViewHolder.like.setImageResource(R.drawable.ic_heart_like_grey);

        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position, List<Object> payloads) {

        if (payloads.contains("like")) {
            CommentViewHolder holder = (CommentViewHolder) holder1;
            ((CommentModel) messagelist.get(position)).setStatus("true");
            holder.like.setImageResource(R.drawable.ic_heart_like);
            holder.liketemp.animate().rotation(-80).setDuration(650).alpha(0.0f).translationYBy(-Utils.pxFromDp(mContext,16)).translationXBy(-Utils.pxFromDp(mContext,70)).setInterpolator(new DecelerateInterpolator()).start();
            holder.likenumber.setText(String.valueOf(Integer.parseInt(((CommentModel) messagelist.get(position)).getUpvotes()) + 1));
            ((CommentModel) messagelist.get(position)).setUpvotes(String.valueOf(Integer.parseInt(((CommentModel) messagelist.get(position)).getUpvotes()) + 1));


        } else if (payloads.contains("unlike")) {
            CommentViewHolder holder = (CommentViewHolder) holder1;

            ((CommentModel) messagelist.get(position)).setStatus("false");
            holder.like.setImageResource(R.drawable.ic_heart_like_grey);
            if (Integer.parseInt(((CommentModel) messagelist.get(position)).getUpvotes()) != 0) {
                holder.likenumber.setText(String.valueOf(Integer.parseInt(((CommentModel) messagelist.get(position)).getUpvotes()) - 1));
                ((CommentModel) messagelist.get(position)).setUpvotes(String.valueOf(Integer.parseInt(((CommentModel) messagelist.get(position)).getUpvotes()) - 1));

            }

        } else
            super.onBindViewHolder(holder1, position, payloads);
    }


    private void settextbackground() {

    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messagelist.get(position) instanceof CommentModel)
            return VIEWTYPE_COMMENT;
        else
            return super.getItemViewType(position);
    }

    public void add(List<Object> list) {
        messagelist.clear();
        messagelist.addAll(list);
        notifyDataSetChanged();

    }

    private void editcomment(final int adapterPosition) {
//Creating a LayoutInflater object for the dialog box
        final LayoutInflater li = LayoutInflater.from(mContext);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_edit_comment, null);
//  number=session.getMobileNumber();
//Initizliaing confirm button fo dialog box and edittext of dialog box
        final Button buttonconfirm = (Button) confirmDialog.findViewById(R.id.buttonConfirm);
        final EditText edittextdesc = (EditText) confirmDialog.findViewById(R.id.edittext);
        edittextdesc.setText(((CommentModel) messagelist.get(adapterPosition)).getComment());
        edittextdesc.setSelection(((CommentModel) messagelist.get(adapterPosition)).getComment().length());
        edittextdesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    buttonconfirm.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.border_pink));
                    buttonconfirm.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                } else {
                    buttonconfirm.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.border_grey));
                    buttonconfirm.setTextColor(Color.parseColor("#9e9e9e"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

        alert.setView(confirmDialog);

        final AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        alertDialog.show();

        //On the click of the confirm button from alert dialog
        buttonconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edittextdesc.getText().toString().trim().length() > 0) {
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edittextdesc.getWindowToken(), 0);
                    alertDialog.dismiss();
                    Toast.makeText(mContext, "updating comment!", Toast.LENGTH_SHORT).show();
                    sendeditcommenttoserver(adapterPosition, edittextdesc.getText().toString().trim());

                } else alertDialog.dismiss();
            }
        });

    }


    private void sendeditcommenttoserver(int adapterPosition, String desc) {
        AndroidNetworking.post("https://www.reweyou.in/google/edit_comment.php")
                .addBodyParameter("uid", userSessionManager.getUID())
                .addBodyParameter("authtoken", userSessionManager.getAuthToken())
                .addBodyParameter("comment", desc)
                .addBodyParameter("commentid", ((CommentModel) messagelist.get(adapterPosition)).getCommentid())
                .setTag("reporst")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: edit: " + response);
                        if (response.contains("Edited")) {
                            Toast.makeText(mContext, "comment updated!", Toast.LENGTH_SHORT).show();

                                ((CommentActivity) mContext).refreshlist();

                        } else
                            Toast.makeText(mContext, "something went wrong!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError);
                        Toast.makeText(mContext, "couldn't connect!", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    private void sendrequestforlikecomment(final int adapterPosition) {
        AndroidNetworking.post("https://www.reweyou.in/google/like.php")
                .addBodyParameter("uid", userSessionManager.getUID())
                .addBodyParameter("imageurl", userSessionManager.getProfilePicture())
                .addBodyParameter("username", userSessionManager.getUsername())
                .addBodyParameter("commentid", ((CommentModel) messagelist.get(adapterPosition)).getCommentid())
                .addBodyParameter("authtoken", userSessionManager.getAuthToken())
                .setTag("reportlike")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: " + anError);
                        notifyItemChanged(adapterPosition, "unlike");
                    }
                });
    }


    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }


   private class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView comment;
        private TextView reply;
        private ImageView image, like, liketemp;
        private TextView username, userlevel, time, edit, likenumber;


        public CommentViewHolder(View inflate) {
            super(inflate);

            image = (ImageView) inflate.findViewById(R.id.image);
            edit = (TextView) inflate.findViewById(R.id.edit);
            username = (TextView) inflate.findViewById(R.id.message);
            likenumber = (TextView) inflate.findViewById(R.id.likenumber);
            like = (ImageView) inflate.findViewById(R.id.like);
            liketemp = (ImageView) inflate.findViewById(R.id.templike);
            comment = (TextView) inflate.findViewById(R.id.comment);
            time = (TextView) inflate.findViewById(R.id.time);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editcomment(getAdapterPosition());
                }
            });


            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((CommentModel) messagelist.get(getAdapterPosition())).getStatus().equals("false"))
                        notifyItemChanged(getAdapterPosition(), "like");
                    else notifyItemChanged(getAdapterPosition(), "unlike");
                    sendrequestforlikecomment(getAdapterPosition());


                }
            });

        }


    }
}
