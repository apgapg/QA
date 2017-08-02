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

import io.github.memfis19.annca.Annca;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;
import qa.reweyou.in.qa.customview.MainFragment;
import qa.reweyou.in.qa.customview.ReplyFragment;
import qa.reweyou.in.qa.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private final static int CAMERA_RQ = 6969;
    private static final int CAPTURE_MEDIA = 77;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        viewPager = findViewById(R.id.viewpager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    @SuppressLint("MissingPermission")
    public void shootvideo(String quesid) {
        Utils.QUES_ID = quesid;
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
            if (BuildConfig.DEBUG)
                Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, ReplyVideoActivity.class);
            i.putExtra("videopath", filePath);
            startActivity(i);

        }
    }

    public void showSecondPage(String queid, String question) {
        ((ReplyFragment) pagerAdapter.getRegisteredFragment(1)).showdata(queid, question);
        viewPager.setCurrentItem(1);
    }


    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 1)
            viewPager.setCurrentItem(0);
        else finish();
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
