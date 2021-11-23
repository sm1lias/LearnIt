package com.smilias.learnit.video_screen

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


@Composable
fun VideoScreen(sourceUrl: String= "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build()
    }

    // We only want to react to changes in sourceUrl.
    // This effect will be executed at each commit phase if
    // [sourceUrl] has changed.
    LaunchedEffect(sourceUrl) {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context,
            Util.getUserAgent(context, context.packageName))

        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(
                // Big Buck Bunny from Blender Project
                sourceUrl
            ))

        exoPlayer.prepare(source)
    }

    // Gateway to traditional Android Views
    AndroidView( factory= { context ->
            PlayerView(context).apply {
                player = exoPlayer
//                playWhenReady = true
            }
        }
    )
}
