package com.example.p7_graphic_animation_2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class IndianFlagView extends View {

    private Paint paint;

    public IndianFlagView(Context context) {
        super(context);
        init();
    }

    public IndianFlagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndianFlagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int stripeHeight = height / 3;

        // Draw the saffron stripe
        paint.setColor(Color.rgb(255, 153, 51));
        canvas.drawRect(0, 0, width, stripeHeight, paint);

        // Draw the white stripe
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, stripeHeight, width, 2 * stripeHeight, paint);

        // Draw the green stripe
        paint.setColor(Color.rgb(19, 136, 8));
        canvas.drawRect(0, 2 * stripeHeight, width, height, paint);

        // Draw the Ashoka Chakra
        paint.setColor(Color.rgb(0, 0, 128));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        float centerX = width / 2;
        float centerY = stripeHeight + (stripeHeight / 2);
        float radius = stripeHeight / 3;
        canvas.drawCircle(centerX, centerY, radius, paint);

        // Draw the 24 spokes
        paint.setStrokeWidth(5);
        for (int i = 0; i < 24; i++) {
            float angle = (float) (i * (2 * Math.PI / 24));
            float startX = centerX + (float) (radius * Math.cos(angle));
            float startY = centerY + (float) (radius * Math.sin(angle));
            canvas.drawLine(centerX, centerY, startX, startY, paint);
        }

        // Reset the paint style
        paint.setStyle(Paint.Style.FILL);
    }
}


