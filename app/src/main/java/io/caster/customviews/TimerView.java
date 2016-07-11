package io.caster.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * A simple custom {@link View} subclass that counts up the number of seconds since it was started.
 * When a TimerView is stopped, the seconds count is reset when the timer next starts.
 */
public class TimerView extends View {

    private Paint backgroundPaint;
    private TextPaint numberPaint;

    private long startTime;

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            updateTimer();
        }
    };

    //
    // Constructors/initialization
    //

    public TimerView(Context context) {
        super(context);
        init();
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.parseColor("#880E4F"));

        numberPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        numberPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));
        numberPaint.setTextSize(64f * getResources().getDisplayMetrics().scaledDensity);
    }

    //
    // Timer interface + helpers
    //

    public void start() {
        startTime = System.currentTimeMillis();

        updateTimer();
    }

    public void stop() {
        startTime = 0;

        removeCallbacks(updateRunnable);
    }

    private void updateTimer() {
        invalidate();

        postDelayed(updateRunnable, 200L);
    }

    //
    // View overrides
    //

    @Override
    protected void onDraw(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        float centerX = Math.round(canvasWidth * 0.5f);
        float centerY = Math.round(canvasHeight * 0.5f);

        // Calculate radius of background.
        float radius = (canvasWidth < canvasHeight ? canvasWidth : canvasHeight) * 0.5f;

        // Calculate elapsed # of seconds.
        String number = String.valueOf((long) ((System.currentTimeMillis() - startTime) * 0.001));
        // Calculate offsets for positioning text.
        float textOffsetX = numberPaint.measureText(number) * 0.5f;
        float textOffsetY = numberPaint.getFontMetrics().ascent * -0.4f;

        // Draw background.
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint);
        // Draw # of seconds.
        canvas.drawText(number, centerX - textOffsetX, centerY + textOffsetY, numberPaint);
    }
}
