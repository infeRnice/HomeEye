package project.Cameras.ui.componentsDoors

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import project.Cameras.R
import project.Cameras.models.Door

@Composable
fun DoorContent(door: Door) {
    Column(modifier = Modifier.padding(0.dp)) {
        door.snapshot?.let { url ->
            Box(contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = url,
                    contentDescription = door.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                PlayIconOverlay(onClick = { /* Обработчик нажатия play */ })
            }
        }
        Text(
            text = door.name,
            modifier = Modifier.padding(15.dp)
        )
    }
}

@Composable
fun PlayIconOverlay(onClick: () -> Unit) {
    // Используйте painterResource, если ваше изображение находится в папке drawable
    Box {
        Image(
            painter = painterResource(id = R.drawable.play),
            contentDescription = "Play",
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center)
                .clickable(onClick = onClick)
        )
    }
}