package project.Cameras.ui.componentsCameras

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
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