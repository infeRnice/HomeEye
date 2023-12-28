package project.Cameras.ui.componentsCameras

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
import project.Cameras.models.Camera

@Composable
fun CameraContent(camera: Camera, onClick: () -> Unit) {
    Column(modifier = Modifier.padding(0.dp)) {
        Box/*(contentAlignment = Alignment.Center)*/ {
            AsyncImage(
                model = camera.snapshot,
                contentDescription = camera.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
            /*PlayIconOverlay(onClick = {  *//*Обработчик нажатия play*//*  })*/
            Image(
                painter = painterResource(id = R.drawable.play),
                contentDescription = "Play",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center) // Выравнивание по центру
                    .clickable(onClick = onClick)
            )
            if (camera.favorites) {
                println("Displaying favorite icon for camera ${camera.id}")
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                )
            }
        }
        Text(
            text = camera.name,
            modifier = Modifier.padding(15.dp)
        )
    }
}