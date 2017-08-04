package qa.reweyou.in.qa;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

import qa.reweyou.in.qa.classes.UserSessionManager;
import qa.reweyou.in.qa.customview.AlertDialogBox;


public class VideoDisplay extends AppCompatActivity implements OnPreparedListener {

    private VideoView emVideoView;
    private TextView headline;
    private TextView description;
    private ImageView like,gradient;
    private UserSessionManager userSessionManager;
    private String ansid;
    private String TAG = VideoDisplay.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userSessionManager = new UserSessionManager(this);
        like = findViewById(R.id.like);
        gradient = findViewById(R.id.gradient);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestupdatelike();
            }
        });

        Intent i = getIntent();
        String url = i.getStringExtra("url");
        ansid = i.getStringExtra("ansid");


        emVideoView = (VideoView) findViewById(R.id.video_view);
        emVideoView.setOnPreparedListener(this);

        emVideoView.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(Exception e) {
                emVideoView.stopPlayback();
                {
                    AlertDialogBox alertDialogBox = new AlertDialogBox(VideoDisplay.this, "Error", "Can't play video.", "okay", null) {
                        @Override
                        public void onNegativeButtonClick(DialogInterface dialog) {

                        }

                        @Override
                        public void onPositiveButtonClick(DialogInterface dialog) {
                            dialog.dismiss();
                            finish();
                        }
                    };
                    alertDialogBox.setCancellable(false);
                    alertDialogBox.show();
                }
                Log.w("error", "error");

                return false;
            }
        });
        emVideoView.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion() {
                emVideoView.restart();
            }
        });
        emVideoView.getVideoControls().setCanHide(false);
        //For now we just picked an arbitrary item to play.  More can be found at
        //https://archive.org/details/more_animation
        emVideoView.setVideoURI(Uri.parse(url));


    }

    private void requestupdatelike() {
        AndroidNetworking.post("https://www.reweyou.in/interview/upvote.php")
                .addBodyParameter("uid", userSessionManager.getUID())
                .addBodyParameter("authtoken", userSessionManager.getAuthToken())
                .addBodyParameter("ansid", ansid)
                .setTag("like")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        Toast.makeText(VideoDisplay.this, "" + response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: " + anError);
                    }
                });
    }

    @Override
    public void onPrepared() {
        //Starts the video playback as soon as it is ready
        emVideoView.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                like.animate().scaleX(1).scaleY(1).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(300).start();
               gradient.animate().scaleX(1).scaleY(1).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(300).start();

            }
        }, 1000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        emVideoView.pause();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
