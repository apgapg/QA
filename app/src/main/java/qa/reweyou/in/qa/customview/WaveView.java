package qa.reweyou.in.qa.customview;

/**
 * This is library implementation of Wave View
 * Wave view is structured on the Sine Wave formula
 * The sine wave formula is :
 * <p>
 * y(t) = A sin(2πft + ρ) = A sin(ωt + ρ)
 * <p>
 * The above formula can be explained in sound terms as follows:
 * <p>
 * y = amplitude X sin ( 2π ( velocity of rotation in cycles per second))
 * <p>
 * Increasing the amplitude of the sine wave, how high the tops and bottoms of the wave go, increases the volume.
 * Increasing or decreasing the cycle rate, how many cycles over distance/time, increases and decreases the pitch of the sound – how high or low the tone sounds.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import qa.reweyou.in.qa.R;

public class WaveView extends View {

    /**
     * To run animation in a infinite loop handler and runnable is used
     * In every 16ms difference the wave is updated
     */
    private Handler handler;

    private Context mContext = null;

    /**
     * @width - Device screen width
     */
    private int width = 0;

    /**
     * x, y1, y2 are used to plot the path for wave
     */
    float x;
    float y1, y2;

    private Paint firstWaveColor;
    private Paint secondWaveColor;

    /**
     * @frequency - Then less the frequency more will be the number of
     * waves
     */
    int frequency = 180;

    /**
     * @amplitude - Amplitude gives the height of wave
     */

    int amplitude = 20;
    private float shift = 0;

    private int quadrant;

    Path firstWavePath = new Path();
    Path secondWavePath = new Path();

    /**
     * @speed - Its the velocity of wave in x-axis
     */
    //private float speed = (float) 0.5;
    private float speed = 0;
    private Paint paint;
    private RectF rectF;

    public WaveView(Context context) {
        super(context);
        init(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mContext = context;
       /* firstWaveColor = new Paint();
        firstWaveColor.setAntiAlias(true);
        firstWaveColor.setStrokeWidth(2);
        firstWaveColor.setColor(Color.parseColor("#64B5F6"));

        secondWaveColor = new Paint();
        secondWaveColor.setAntiAlias(true);
        secondWaveColor.setStrokeWidth(2);
        secondWaveColor.setColor(Color.parseColor("#2196F3"));*/

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mContext.getResources().getColor(R.color.colorPrimary));
        paint.setAntiAlias(true);
        rectF = new RectF();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       /* quadrant = getHeight();
        width = canvas.getWidth();

        firstWavePath.moveTo(0, getHeight());
        secondWavePath.moveTo(0, getHeight());
        firstWavePath.lineTo(0, quadrant);
        secondWavePath.lineTo(0, quadrant*2);

        for (int i = 0; i < width + 10; i = i + 10) {
            x = (float) i;

            y1 = quadrant + amplitude * (float) Math.sin(((i + 10) * Math.PI / frequency) + shift);
            y2 = quadrant * 2 + amplitude * (float) Math.sin(((i + 10) * Math.PI / frequency) + shift);

            firstWavePath.lineTo(x, y1);
            secondWavePath.lineTo(x, y2);
        }
        firstWavePath.lineTo(getWidth(), getHeight());
        secondWavePath.lineTo(getWidth(), getHeight());
        canvas.drawPath(firstWavePath, firstWaveColor);
        canvas.drawPath(secondWavePath, secondWaveColor);*/
        rectF.set((float) (-getWidth() / 1.5), -getWidth(), (float) ((1+(1/1.5)) * getWidth()), getHeight());
        canvas.drawArc(rectF, 0, 180, false, paint);
    }


}
