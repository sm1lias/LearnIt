package com.smilias.learnit.menu_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
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
import com.smilias.learnit.video_screen.VideoScreenViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: MenuScreenViewModel = hiltViewModel()
) {

    LazyVerticalGrid(
        cells = GridCells.Fixed(2), contentPadding = PaddingValues(5.dp)
    ){
        items(viewModel.videoList.size) { index ->
            val videoInfo = viewModel.videoList[index]
            VideoCard(
                painter = painterResource(id = videoInfo.thumb),
                contentDescription =videoInfo.description,
                title =videoInfo.title,
                modifier =Modifier.fillMaxWidth(),
                url = videoInfo.source,
                navController
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
    navController: NavController
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(200.dp).clickable {
            navController.navigate(Screen.VideoScreen.route+"/"+url.replace("/", "\\"))
        },
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
                contentAlignment = Alignment.BottomStart
            ) {
                Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
    }
}