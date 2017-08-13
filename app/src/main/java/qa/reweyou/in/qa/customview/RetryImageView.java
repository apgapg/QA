package qa.reweyou.in.qa.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by master on 12/8/17.
 */

public class RetryImageView extends android.support.v7.widget.AppCompatImageView {
    private ValueAnimator valueAnimator;

    public RetryImageView(Context context) {
        super(context);
        init();
    }


    public RetryImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RetryImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        valueAnimator = ValueAnimator.ofFloat(0, 360);
        valueAnimator.setDuration(600);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setRotation((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                setRotation(0);
                super.onAnimationStart(animation);
            }
        });

    }

    public void startRotateAnimation() {
        setVisibility(VISIBLE);
        valueAnimator.start();
    }

    public void stopRotateAnimation() {
        valueAnimator.end();
        setVisibility(INVISIBLE);
    }

    public void freezeRotateAnimation() {
        setVisibility(VISIBLE);
        valueAnimator.cancel();
        setRotation(0);
    }
}
