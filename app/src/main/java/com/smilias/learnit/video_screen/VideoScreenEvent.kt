package com.smilias.learnit.video_screen

sealed class VideoScreenEvent{
    object OnStartCapture: VideoScreenEvent()
    object OnStopCapture: VideoScreenEvent()
}
