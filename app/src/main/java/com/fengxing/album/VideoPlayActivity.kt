package com.fengxing.album

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.MediaController
import android.widget.VideoView
import kotlin.time.Duration.Companion.parse

class VideoPlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)

        val videoView = findViewById<VideoView>(R.id.video)
        videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.video))
        videoView.start()

        videoView.setOnCompletionListener { videoView.start() }
    }
}