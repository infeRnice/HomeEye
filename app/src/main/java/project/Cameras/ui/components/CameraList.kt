package project.Cameras.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import project.Cameras.data.CamerasRealmDatabase
import project.Cameras.models.Camera
import project.Cameras.presentation.CamerasViewModel
import project.Cameras.ui.theme.GrayBack

@Composable
fun CameraListByRoom(
    cameras: LiveData<List<Camera>>,
    viewModel: CamerasViewModel
    ) {
    val cameraList by cameras.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    // Группировка камер по комнатам
    val camerasByRoom = cameraList.groupBy { it.room }
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.loadDoors() },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = GrayBack
        ) {
            LazyColumn {
                camerasByRoom.forEach { (room, camerasInRoom) ->
                    item {
                        val roomName = when (room) {
                            "FIRST" -> "Гостинная"
                            else -> room ?: "Другие камеры"
                        }
                        Text(
                            text = roomName,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                letterSpacing = 0.25.sp
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    items(camerasInRoom) { camera ->
                        SwipeableCameraItem(camera = camera)
                    }
                }
            }
        }
    }
}

/*@Composable
fun CameraItem(camera: Camera) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            AsyncImage(
                model = camera.snapshot,
                contentDescription = camera.name,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = camera.name,
                modifier = Modifier.padding(16.dp)
            )
            StarIconRow() // Вызов компонента для иконки "star"
        }
    }
}

@Composable
fun StarIconRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { *//* Обработчик нажатия star *//* }) {
            Icon(
                Icons.Filled.Star,
                contentDescription = "Star",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}*/

/*@Composable
fun CameraItem(camera: Camera) {
    val cardColors = CardDefaults.cardColors(
        containerColor = Color.White
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = MaterialTheme.shapes.medium,
        colors = cardColors
    ) {
        Column {
            AsyncImage(
                model = camera.snapshot,
                contentDescription = camera.name,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = camera.name,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}*/