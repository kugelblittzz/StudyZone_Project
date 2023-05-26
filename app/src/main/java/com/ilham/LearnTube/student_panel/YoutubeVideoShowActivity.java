package com.ilham.LearnTube.student_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.ilham.LearnTube.R;

import java.net.MalformedURLException;
import java.net.URL;

public class YoutubeVideoShowActivity extends AppCompatActivity {

    private String title, url;
    private TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video_show);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("link").toString();

        details = findViewById(R.id.textDetails);
        details.setText(title);
        String videoId;
        URL urll = null;
        try {
            urll = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        url = urll.getQuery();

        if(url.contains("watch?v=")){
            String[] arrOfStr = url.split("watch?v=");
            System.out.println(arrOfStr[0]);
            videoId = url.split("watch?v=")[1];
            System.out.println(videoId);
        } else if(url.contains("youtu.be/")){
            videoId = url.split("youtu.be/")[1];
        } else {
            videoId = url;
        }


        Toast.makeText(YoutubeVideoShowActivity.this,videoId,Toast.LENGTH_SHORT).show();
        //videoId="uYPbbksJxIg";

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        try {
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(videoId.split("v=")[1], 0);
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Url expired! Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}