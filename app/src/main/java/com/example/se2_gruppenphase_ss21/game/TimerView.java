package com.example.se2_gruppenphase_ss21.game;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.se2_gruppenphase_ss21.R;


/**
 * A TIMER as a VIEW.
 * It is represented as a pie shape that reduces clockwise.
 *
 * It is started with the start method and then runs a countdown until the specified
 * finishUntil parameter.
 *
 * @author Manuel Simon #00326348
 */
public class TimerView extends View {
    private Bitmap bitmap;
    private Canvas canvas;
    private final Paint paint;
    private RectF bounds;
    private float angleSpan = -360f;
    private int alertTime = 15000;
    // refresh-rate in ms - default of 50ms resembles 20fps
    private int delta = 50;

    private volatile boolean abort;
    private TimerListener listener;


    /**
     * Sets the properties by reading this Views attributes.
     * At the moment this is limited to the timers standard color.
     * @param ctx context
     * @param attrs attributes of the view
     */
    public TimerView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        int color = Color.BLUE;

        // check for arguments
        if(attrs != null) {
            TypedArray ta = ctx.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.TimerView, 0, 0);
            // check if a specific color has been declared
            if(ta != null) {
                color = ta.getColor(R.styleable.TimerView_color, Color.BLUE);
                ta.recycle();
            }
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
    }

    /**
     * Sets up the Views underlying bitmap after the view is loaded or resized.
     * @param w Views new width
     * @param h Views new height
     * @param oldW Views old width
     * @param oldH Views old height
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        if (w != oldW || h != oldH) {
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.TRANSPARENT);
            canvas = new Canvas(bitmap);
        }

        bounds = new RectF(0,0, getWidth(), getHeight());
        invalidate();
    }

    /**
     * Draws the pie timer with the Views current state.
     * @param c Canvas to draw on.
     */
    @Override
    protected void onDraw(Canvas c) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawArc(bounds, -90f, angleSpan, true, paint);
        c.drawBitmap(bitmap, 0f, 0f, null);
    }

    /**
     * This method starts the Timer.
     *
     * The timer runs until it meets the provided time limit (finishUntil). This ensures that
     * program specific performance issues won't interfere with the run time of the timer.
     *
     * It creates a separate thread which updates the View in regular intervals. Those intervals
     * are determined by the delta time (time in ms between each update).
     *
     * @param finishUntil time until the timer finishes
     */
    public void start(long finishUntil) {

        long totalTime = finishUntil - System.currentTimeMillis();

        Thread timer = new Thread(() -> {
            // Handler is not necessary in this case, but it's good practise
            Handler handler = new Handler(Looper.getMainLooper());

            while(System.currentTimeMillis() < finishUntil && !abort) {

                handler.post(() -> {
                    long remainTime = finishUntil - System.currentTimeMillis();
                    if(remainTime < alertTime && getColor() != Color.RED)
                        setColor(Color.RED);
                    // update angleSpan with remaining time
                    angleSpan = -360f * ((float)remainTime / (float)totalTime);
                    invalidate();
                });

                try {
                    Thread.sleep(delta);
                } catch (InterruptedException ex) {
                    Log.e("timer", ex.toString());
                    Thread.currentThread().interrupt();
                }
            }

            listener.timeIsUp(this);
        });

        timer.start();
    }

    /**
     * Sets the delta time by specifying a desired FPS (frames per second) value.
     * @param fps the desired FPS.
     */
    public void setFPS(int fps) {
        delta = 1000 / fps;
    }

    /**
     * Sets the color of the timer.
     * @param color color to be set.
     */
    public void setColor(int color) {
        paint.setColor(color);
    }

    /**
     * Returns the current color of the timer.
     * @return the current color of the timer.
     */
    public int getColor() {
        return paint.getColor();
    }

    /**
     * Lets you set the alert time. The alert time is the remaining time at which the timer
     * will switch to the alert-color to indicate minimal remaining time.
     * @param alertTime remaining time in ms at which timer will turn red.
     */
    public void setAlertTime(int alertTime) {
        this.alertTime = alertTime;
    }

    /**
     * Gets the remaining time at which the timer will turn red to indicate minimal time left.
     * @return remaining time in ms at which timer will turn red.
     */
    public int getAlertTime() {
        return alertTime;
    }

    /**
     * Sets the listener to react on finished timer.
     * @param listener the listener to set.
     */
    public void setListener(TimerListener listener) {
        this.listener = listener;
    }

    public void abort() {
        abort = true;
        Log.d("timer", "Timer " + this + " aborted!");
    }
}
