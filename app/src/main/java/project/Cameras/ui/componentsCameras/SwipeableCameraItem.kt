package project.Cameras.ui.componentsCameras

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import coil.compose.AsyncImage
import project.Cameras.R
import project.Cameras.models.Camera
import project.Cameras.ui.theme.GrayBack
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class, ExperimentalWearMaterialApi::class)
@Composable
fun SwipeableCameraItem(
    camera: Camera,

    ) {
    // Параметры свайпа
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val anchors = mapOf(0f to 0, -350f to 1) // Якоря для свайпа

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .padding(10.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
    ) {
        // Фон с иконкой "star"
        StarIconRow(
            modifier = Modifier
                .matchParentSize()
                .padding(16.dp)
                .align(Alignment.CenterEnd)
                .background(GrayBack)
        )

        // Передний слой (свайпаемый)
        Card(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(contentAlignment = Alignment.Center) {
                CameraContent(camera)
            }
        }
    }
}

@Composable
fun CameraContent(camera: Camera) {
    Column(modifier = Modifier.padding(0.dp)) {
        Box(contentAlignment = Alignment.Center) {
            AsyncImage(
                model = camera.snapshot,
                contentDescription = camera.name,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            PlayIconOverlay(onClick = { /* Обработчик нажатия play */ })
        }
        Text(
            text = camera.name,
            modifier = Modifier.padding(15.dp)
        )
    }
}

@Composable
fun StarIconRow(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Обработчик нажатия star */ }) {
            Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Gray)
        }
    }
}

@Composable
fun PlayIconOverlay(onClick: () -> Unit) {
    // Используйте painterResource, если ваше изображение находится в папке drawable
    val playIcon: Painter = painterResource(id = R.drawable.play)

    Image(
        painter = playIcon,
        contentDescription = "Play",
        modifier = Modifier.size(48.dp)
            .clickable(onClick = onClick)
    )

}