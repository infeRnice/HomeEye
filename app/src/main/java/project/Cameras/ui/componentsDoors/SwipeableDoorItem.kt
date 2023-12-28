import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import project.Cameras.data.CamerasRealmDatabase
import project.Cameras.models.Door
import project.Cameras.presentation.CamerasViewModel
import project.Cameras.ui.componentsDoors.DoorContent
import project.Cameras.ui.componentsDoors.ShowRenameDoorDialog
import project.Cameras.ui.theme.GrayBack
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalWearMaterialApi::class)
@Composable
fun SwipeableDoorItem(
    door: Door,
    realmDatabase: CamerasRealmDatabase,
    viewModel: CamerasViewModel
) {
    var showRenameDoorDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var startRenaming by remember { mutableStateOf(false) }

    if (showRenameDoorDialog) {
        ShowRenameDoorDialog(
            door.id,
            onRename = { newName = it; startRenaming = true },
            onDismiss = { showRenameDoorDialog = false }
        )
    }
    // Проверяем, нужно ли начать процесс переименования
    if (startRenaming && newName.isNotEmpty()) {
        LaunchedEffect(newName) {
            realmDatabase.updateDoorName(door.id, newName)
            val updatedDoor = door.copy(name = newName) // Создаем обновленный объект двери
            viewModel.updateDoorInList(updatedDoor) // Обновляем дверь в списке
            startRenaming = false // Сброс флага после обновления
        }
    }

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
        // Фон с иконками
        IconRow(
            modifier = Modifier
                .matchParentSize()
                .padding(16.dp)
                .align(Alignment.CenterEnd)
                .background(GrayBack),
            onEditClick = { showRenameDoorDialog = true }
        )

        // Передний слой (свайпаемый)
        Card(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            DoorContent(door)
        }
    }
}

@Composable
fun IconRow(modifier: Modifier, onEditClick: () -> Unit) {
    Row(
        modifier = modifier.padding(end = 2.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically

    ) {
        IconButton(onClick = onEditClick) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "Edit",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
                )
        }
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = { /* Обработчик нажатия star */ }) {
            Icon(Icons.Filled.Star,
                contentDescription = "Star",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp))
        }
    }
}

