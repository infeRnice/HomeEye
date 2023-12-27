package project.Cameras.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import project.Cameras.CamerasApplication
import project.Cameras.data.CamerasRealmDatabase
import project.Cameras.data.repository.DataRepository
import project.Cameras.network.ApiService
import project.Cameras.network.createApiService
import project.Cameras.ui.components.HomeScreen
import project.Cameras.ui.theme.CamerasTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamerasTheme {
                val app = application as CamerasApplication

                val realmDatabase = app.realmDatabase
                val apiService = app.apiService

                val repository = DataRepository(apiService, realmDatabase)

                val viewModelFactory = CamerasViewModelFactory(repository, realmDatabase)
                val viewModel: CamerasViewModel by viewModels { viewModelFactory }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(viewModel = viewModel)
                }
            }
        }
    }
}

