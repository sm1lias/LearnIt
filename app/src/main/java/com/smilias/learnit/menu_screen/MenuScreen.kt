package com.smilias.learnit.menu_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smilias.learnit.Screen
import com.smilias.learnit.VideoList
import com.smilias.learnit.utils.UiEvent
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MenuScreen(
    onNavigate: (UiEvent.Navigate) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(10.dp)
    ) {
        items(VideoList.getVideoList().size) { index ->
            val videoInfo = VideoList.getVideoList()[index]
            VideoCard(
                painter = painterResource(id = videoInfo.thumb),
                contentDescription = videoInfo.description,
                title = videoInfo.title,
                modifier = Modifier.fillMaxSize(),
                url = videoInfo.source,
                onNavigate = onNavigate
            )
        }
    }

}

@Composable
fun VideoCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier,
    url: String,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .clickable {
                    onNavigate(
                        UiEvent.Navigate(
                            Screen.VideoScreen.route + "/" + URLEncoder.encode(
                                url,
                                StandardCharsets.UTF_8.toString()
                            )
                        )
                    )
                }, contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}