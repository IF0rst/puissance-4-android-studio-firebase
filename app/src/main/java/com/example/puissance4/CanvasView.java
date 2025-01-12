package com.example.puissance4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {

    private String Text = "Ce puissance 4 en ligne à été programmé et designé par Pablo Huys // Ce canvas utilise des Runnables à l'inverse de la classe GameHandler qui utilise des Threads. :)";
    private Paint _Paint;
    private float TextWidth;
    private float TextX = 100f;

    private Handler _Handler = new Handler();
    private Runnable ScrollTextRunnable = new Runnable() {
        @Override
        public void run() {
            TextX -= 2;
            if (TextX + TextWidth < 0) {
                TextX = getWidth();
            }
            invalidate();
            _Handler.postDelayed(this, 20);
        }
    };

    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        _Paint = new Paint();
        _Paint.setColor(0xFF0000FF);
        _Paint.setTextSize(60);
        _Paint.setAntiAlias(true);
        _Paint.setTextAlign(Paint.Align.LEFT);
        TextWidth = _Paint.measureText(Text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(Text, TextX, getHeight() / 2, _Paint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        _Handler.post(ScrollTextRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        _Handler.removeCallbacks(ScrollTextRunnable);
    }
}
