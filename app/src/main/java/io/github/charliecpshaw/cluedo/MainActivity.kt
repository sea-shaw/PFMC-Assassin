package io.github.charliecpshaw.cluedo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import io.github.charliecpshaw.cluedo.ui.theme.CluedoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CluedoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CluedoApp()
                }
            }
        }
    }
}
