package com.example.trl
import com.example.trl.ai.Logger
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.trl.ui.screens.HomeScreen
import com.example.trl.ui.theme.TrlTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Verify Groq API key is being injected
        Logger.d("Groq Key Length = ${BuildConfig.GROQ_API_KEY.length}")
        Logger.d("Groq Key Prefix = ${BuildConfig.GROQ_API_KEY.take(8)}")
        setContent {
            TrlTheme {
                HomeScreen()
            }
        }
    }
}