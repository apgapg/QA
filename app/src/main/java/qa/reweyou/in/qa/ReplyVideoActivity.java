package qa.reweyou.in.qa;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

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
        quesid = getIntent().getStringExtra("quesid");
        file = new File(videourl);
        ImageView imageView = findViewById(R.id.image);
        Glide.with(ReplyVideoActivity.this).load(videourl).into(imageView);


        userSessionManager = new UserSessionManager(this);
        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom_upload_dialog = new Custom_upload_dialog();
                custom_upload_dialog.setCancelable(false);
                custom_upload_dialog.show(getSupportFragmentManager(), "");

                Glide.with(ReplyVideoActivity.this).load(videourl).asBitmap().toBytes(Bitmap.CompressFormat.JPEG, 90).override(500, 500).into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                        uploadPost(Base64.encodeToString(resource, Base64.DEFAULT));
                    }
                });

            }
        });


    }

    private void uploadPost(String encodedImage) {
        AndroidNetworking.upload(url)
                .addMultipartFile("myFile", file)
                .addMultipartParameter("uid", userSessionManager.getUID())
                .addMultipartParameter("auth", userSessionManager.getAuthToken())
                .addMultipartParameter("image", encodedImage)
                .addMultipartParameter("queid", Utils.QUES_ID)
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
                        if (response.contains("Successfully Uploaded")) {
                            custom_upload_dialog.dismiss();
                            Toast.makeText(ReplyVideoActivity.this, "upload success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else
                            Toast.makeText(ReplyVideoActivity.this, "something went wrong!", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onError: " + anError);
                        custom_upload_dialog.dismiss();
                        Toast.makeText(ReplyVideoActivity.this, "upload error", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
