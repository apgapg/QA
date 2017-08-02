package qa.reweyou.in.qa;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import qa.reweyou.in.qa.classes.UserSessionManager;
import qa.reweyou.in.qa.customview.Custom_upload_dialog;
import qa.reweyou.in.qa.utils.NetworkHandler;

public class AskQuestion extends AppCompatActivity {

    private UserSessionManager userSessionManager;
    private String TAG=AskQuestion.class.getName();
    private Custom_upload_dialog custom_upload_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        userSessionManager = new UserSessionManager(this);
        final EditText editText = findViewById(R.id.edittext);
        final EditText hashtag = findViewById(R.id.hashtag);
        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length() > 0) {
                    if (hashtag.getText().toString().trim().length() > 0) {
                        custom_upload_dialog = new Custom_upload_dialog();
                        custom_upload_dialog.setCancelable(false);
                        custom_upload_dialog.show(getSupportFragmentManager(), "");

                        uploadPost(editText.getText().toString().trim(), hashtag.getText().toString().trim());
                    } else
                        Toast.makeText(AskQuestion.this, "Hashtag cannot be empty!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(AskQuestion.this, "Question cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadPost(String ques, String hashtag) {
        AndroidNetworking.post("https://www.reweyou.in/interview/ask_questions.php")
                .addBodyParameter("uid", userSessionManager.getUID())
                .addBodyParameter("authtoken", userSessionManager.getAuthToken())
                .addBodyParameter("question", ques)
                .addBodyParameter("hashtag", hashtag)
                .setTag("fetchgroups")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);

                        custom_upload_dialog.dismiss();
                        if(response.contains("Successfully uploaded")){
                            finish();
                        }else Toast.makeText(AskQuestion.this,"Something went wrong!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError: "+anError);
                        Toast.makeText(AskQuestion.this,"Check Connection!",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
