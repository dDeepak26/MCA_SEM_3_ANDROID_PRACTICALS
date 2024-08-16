package com.example.p9_image_video;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private VideoView videoView;
    private Button showImageButton;
    private Button playVideoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);
        showImageButton = findViewById(R.id.showImageButton);
        playVideoButton = findViewById(R.id.playVideoButton);

        showImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayImage();
            }
        });

        playVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });
    }

    private void displayImage() {
        // Replace R.drawable.sample_image with your actual image resource
        imageView.setImageResource(R.drawable.sample_image);
        imageView.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
    }

    private void playVideo() {
        imageView.setVisibility(View.GONE);
        videoView.setVisibility(View.VISIBLE);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Replace "android.resource://" + getPackageName() + "/" + R.raw.sample_video with your actual video path
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.sample_video;
        videoView.setVideoURI(Uri.parse(videoPath));
        // videoView.start();
    }
}