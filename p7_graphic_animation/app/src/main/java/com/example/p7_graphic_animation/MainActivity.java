package com.example.p7_graphic_animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customView = findViewById(R.id.customView);
        Button startAnimationButton = findViewById(R.id.startAnimationButton);

        startAnimationButton.setOnClickListener(v -> {
            ObjectAnimator animator = ObjectAnimator.ofFloat(customView, "radius", 50f, 300f);
            animator.setDuration(2000);
            animator.start();
        });
    }
}
