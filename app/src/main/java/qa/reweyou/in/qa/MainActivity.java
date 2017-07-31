package qa.reweyou.in.qa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;

import io.github.memfis19.annca.Annca;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;
import qa.reweyou.in.qa.customview.MainFragment;
import qa.reweyou.in.qa.customview.ReplyFragment;

public class MainActivity extends AppCompatActivity {

    private final static int CAMERA_RQ = 6969;
    private static final int CAPTURE_MEDIA = 77;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    @SuppressLint("MissingPermission")
    public void shootvideo() {
        //  File saveFolder = new File(Environment.getExternalStorageDirectory(), "Reweyous");
      /*  if (!saveFolder.mkdirs())
            throw new RuntimeException("Unable to create save directory, make sure WRITE_EXTERNAL_STORAGE permission is granted.");
*/
       /* new MaterialCamera(this)
                .countdownMinutes(1.5f)
                .defaultToFrontFacing(true)
                .allowChangeCamera(true)
                // Sets a custom bit rate for audio recording.

                .qualityProfile(MaterialCamera.QUALITY_480P)       // Sets a quality profile, manually setting bit rates or frame rates with other settings will overwrite individual quality profile settings

                .start(CAMERA_RQ);*/

        AnncaConfiguration.Builder videoLimited = new AnncaConfiguration.Builder(this, CAPTURE_MEDIA);
        videoLimited.setMediaAction(AnncaConfiguration.MEDIA_ACTION_VIDEO);
        videoLimited.setMediaQuality(AnncaConfiguration.MEDIA_QUALITY_AUTO);
        videoLimited.setVideoFileSize(10 * 1024 * 1024);
        videoLimited.setCameraFace(AnncaConfiguration.CAMERA_FACE_FRONT);
        videoLimited.setMinimumVideoDuration(100 * 1000);
        new Annca(videoLimited.build()).launchCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Received recording or error from MaterialCamera
        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH);
            Toast.makeText(this,filePath,Toast.LENGTH_SHORT).show();
        }
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MainFragment();
                case 1:
                    return new ReplyFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

}
