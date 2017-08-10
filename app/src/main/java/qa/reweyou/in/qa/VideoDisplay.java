package qa.reweyou.in.qa;

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

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

import qa.reweyou.in.qa.classes.UserSessionManager;


public class VideoDisplay extends AppCompatActivity implements EasyVideoCallback {

    private VideoView emVideoView;

    private ImageView like, gradient;
    private UserSessionManager userSessionManager;
    private String ansid;
    private String TAG = VideoDisplay.class.getName();
    private EasyVideoPlayer player;
    private String queid;

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
        queid = i.getStringExtra("queid");


      /*  emVideoView = (VideoView) findViewById(R.id.video_view);
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
        emVideoView.setVideoURI(Uri.parse(url));*/


        player = (EasyVideoPlayer) findViewById(R.id.player);

        // Sets the callback to this Activity, since it inherits EasyVideoCallback
        player.setCallback(this);

        // Sets the source to the HTTP URL held in the TEST_URL variable.
        // To play files, you can use Uri.fromFile(new File("..."))
        player.setSource(Uri.parse(url));


        findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VideoDisplay.this, CommentActivity.class);
                i.putExtra("ansid", ansid);
                i.putExtra("queid", queid);
                startActivity(i);
            }
        });

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

   /* @Override
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

    }*/

    /*@Override
    protected void onPause() {
        super.onPause();
        emVideoView.pause();
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {

    }

    @Override
    public void onPaused(EasyVideoPlayer player) {

    }

    @Override
    public void onPause() {
        super.onPause();
        // Make sure the player stops playing if the user presses the home button.
        player.pause();
    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {

    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                like.animate().scaleX(1).scaleY(1).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(300).start();
                gradient.animate().scaleX(1).scaleY(1).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(300).start();

            }
        }, 1000);


    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {

    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {

    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {

    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {

    }
}
