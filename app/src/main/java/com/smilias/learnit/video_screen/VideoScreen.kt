package com.smilias.learnit.video_screen

import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.smilias.learnit.utils.foregroundStartService


@Composable
fun VideoScreen(
    sourceUrl: String = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
    viewModel: VideoScreenViewModel = hiltViewModel()
) {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current



    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        viewModel.exoPlayer
    }

    // We only want to react to changes in sourceUrl.
    // This effect will be executed at each commit phase if
    // [sourceUrl] has changed.
    LaunchedEffect(sourceUrl) {
        viewModel.videoUrl.value = sourceUrl
        viewModel.getVideoTitleFromUrl()
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.packageName)
        )

        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(
                Uri.parse(
                    // Big Buck Bunny from Blender Project
                    sourceUrl
                )
            )

        exoPlayer.value.prepare(source)
    }
    LaunchedEffect(viewModel.playing) {
        if (viewModel.playing) {
            context.foregroundStartService("Start")
            viewModel.startCapturing()
        } else {
            context.foregroundStartService("Exit")
            viewModel.stopCapturing()
        }
    }

    // Gateway to traditional Android Views
    AndroidView(factory = { context ->
        PlayerView(context).apply {
            player = exoPlayer.value
        }
    }
    )
}
