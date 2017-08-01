package qa.reweyou.in.qa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;

import java.io.File;

import qa.reweyou.in.qa.classes.UserSessionManager;
import qa.reweyou.in.qa.customview.Custom_upload_dialog;
import qa.reweyou.in.qa.utils.Utils;

public class ReplyVideoActivity extends AppCompatActivity {

    private EditText hashtag;

    private String url = Utils.URL_PREFFIX + "shoot_answer.php";
    private UserSessionManager userSessionManager;
    private String TAG = ReplyVideoActivity.class.getName();
    private String videourl;
    private File file;
    private Custom_upload_dialog custom_upload_dialog;
    private String quesid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply2);

        videourl = getIntent().getStringExtra("videopath");
        quesid= getIntent().getStringExtra("quesid");
        file = new File(videourl);
        ImageView imageView = findViewById(R.id.image);
        Glide.with(ReplyVideoActivity.this).load(videourl).into(imageView);


        userSessionManager = new UserSessionManager(this);
        hashtag = findViewById(R.id.tag);
        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashtag.getText().toString().trim().length() > 0) {
                    custom_upload_dialog = new Custom_upload_dialog();
                    custom_upload_dialog.setCancelable(false);
                    custom_upload_dialog.show(getSupportFragmentManager(), "");
                    uploadPost();
                } else {
                    Toast.makeText(ReplyVideoActivity.this, "Please provide a tag to this video", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void uploadPost() {
        AndroidNetworking.upload(url)
                .addMultipartFile("video", file)
                .addMultipartParameter("uid", userSessionManager.getUID())
                .addMultipartParameter("authtoken", userSessionManager.getAuthToken())
                .addMultipartParameter("hashtag", hashtag.getText().toString())
                .addMultipartParameter("quesid",Utils.QUES_ID)
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                        Log.d(TAG, "onProgress: " + bytesUploaded + "   total: " + totalBytes);
                    }
                })
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        custom_upload_dialog.dismiss();
                        Toast.makeText(ReplyVideoActivity.this,"upload success",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError);
                        custom_upload_dialog.dismiss();
                        Toast.makeText(ReplyVideoActivity.this,"upload error",Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
