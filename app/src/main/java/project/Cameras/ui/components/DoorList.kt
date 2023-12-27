package project.Cameras.ui.components

import SwipeableDoorItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import project.Cameras.data.CamerasRealmDatabase
import project.Cameras.models.Door
import project.Cameras.presentation.CamerasViewModel
import project.Cameras.ui.theme.GrayBack

@Composable
fun DoorList(
    doors: LiveData<List<Door>>,
    realmDatabase: CamerasRealmDatabase,
    viewModel: CamerasViewModel
) {
    val doorList by doors.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.loadDoors() },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = GrayBack
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                items(doorList.size) { index ->
                    SwipeableDoorItem(
                        door = doorList[index],
                        realmDatabase = realmDatabase,
                        viewModel = viewModel
                    )
                }

            }
        }
    }
}


