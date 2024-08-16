package com.example.p7_graphic_animation_2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ShapesView extends View {

    private Paint paint;

    public ShapesView(Context context) {
        super(context);
        init();
    }

    public ShapesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShapesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw a circle
        paint.setColor(Color.RED);
        canvas.drawCircle(200, 200, 100, paint);

        // Draw a rectangle
        paint.setColor(Color.GREEN);
        canvas.drawRect(400, 100, 600, 300, paint);

        // Draw a line
        paint.setColor(Color.BLUE);
        canvas.drawLine(100, 500, 700, 500, paint);
    }
}

