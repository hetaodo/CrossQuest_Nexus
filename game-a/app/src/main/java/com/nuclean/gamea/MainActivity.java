package com.nuclean.gamea;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static MainActivity instance; // static reference for UI update
    private ImageView key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        key = findViewById(R.id.key);

        // Restore progress: if key was already acquired, show it
        boolean hasKey = getSharedPreferences("progress", MODE_PRIVATE)
                .getBoolean("key:gold", false);

        if (hasKey) {
            key.setVisibility(View.VISIBLE);
        }

        enableKeyDrag();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void enableKeyDrag() {
        key.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        v.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null; // avoid memory leak
    }
}
