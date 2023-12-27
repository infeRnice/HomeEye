package project.Cameras.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun ShowRenameDoorDialog(
    doorId: Int,
    onRename: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Переименовать дверь") },
        text = {
            TextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Новое название") }
            )
        },
        confirmButton = {
            Button(onClick = {
                if (newName.isNotEmpty()) {
                    onRename(newName)
                }
                /*onStartRenaming()*/
                onDismiss()
            }) {
                Text("Переименовать")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}
