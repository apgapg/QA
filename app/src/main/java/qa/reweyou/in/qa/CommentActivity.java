package qa.reweyou.in.qa;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import qa.reweyou.in.qa.classes.CommentFragment;
import qa.reweyou.in.qa.classes.CommentsAdapter;
import qa.reweyou.in.qa.classes.UserSessionManager;


public class CommentActivity extends AppCompatActivity {

    private static final String TAG = CommentActivity.class.getName();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public EditText editText;
    private ImageView send;
    private TextView replyheader;
    private UserSessionManager userSessionManager;
    private String ansid;
    private String tempcommentid;
    private TextView nocommenttxt;
    private CommentsAdapter adapterComment;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ViewPager viewpager;
    private TabLayout tablayout;
    private PagerAdapterSingle pagerAdapterSingle;
    private String queid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ansid=getIntent().getStringExtra("ansid");
        queid=getIntent().getStringExtra("queid");

        viewpager = (ViewPager) findViewById(R.id.viewPager);

        pagerAdapterSingle = new PagerAdapterSingle(getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapterSingle);


    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tablayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    Typeface type = Typeface.createFromAsset(CommentActivity.this.getAssets(), "Quicksand-Medium.ttf");

                    ((TextView) tabViewChild).setTypeface(type);
                }
            }
        }
    }


    @Override
    public void onBackPressed() {

        finish();

    }


    public void refreshlist() {
        // getData();

        ((CommentFragment) pagerAdapterSingle.getRegisteredFragment(0)).getData();

    }


    public void showCommentPage() {
        viewpager.setCurrentItem(1);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }


    private class PagerAdapterSingle extends FragmentStatePagerAdapter {
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();


        private PagerAdapterSingle(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {

            CommentFragment commentFragment = new CommentFragment();
            Bundle bundle = new Bundle();
            bundle.putString("ansid", ansid);
            bundle.putString("queid", queid);
            commentFragment.setArguments(bundle);

            return commentFragment;
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


        @Override
        public int getCount() {
            return 1;
        }


    }

}
