package qa.reweyou.in.qa.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import qa.reweyou.in.qa.utils.Utils;

/**
 * Created by master on 13/8/17.
 */

public class CurveRecyclerView extends RecyclerView {
    private Path clipPath;
    private int width;
    private int height;

    public CurveRecyclerView(Context context) {
        super(context);
        init();
    }

    public CurveRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurveRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        clipPath = new Path();
       /* clipPath.moveTo(0, 0);
        clipPath.addRoundRect(rectF, 400, 400, Path.Direction.CW);*/


    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.clipPath(clipPath);
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        this.width = w;
        this.height = h;
        int offset = (int) Utils.pxFromDp(getContext(), 5);
        clipPath.reset();
        clipPath.moveTo(0, offset);

        clipPath.lineTo(0, height);

        clipPath.lineTo(w, height);

        clipPath.lineTo(width, offset);

        clipPath.cubicTo(width, offset, (width + Utils.pxFromDp(getContext(), 1)) / 2, Utils.pxFromDp(getContext(), 49), 0, offset);


        super.onSizeChanged(w, h, oldw, oldh);
    }
}
