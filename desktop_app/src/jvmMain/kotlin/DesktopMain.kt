import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.khalekuzzamanjustcse.taskmanagement.features.root_home.RootModule


fun main() {
    application {
        Window(
            title = "Compose Desktop",
            onCloseRequest = ::exitApplication
        ) {
            MaterialTheme {
                RootModule()
            }
        }
    }

}

