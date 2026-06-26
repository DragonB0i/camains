package com.example.trl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.trl.ui.screens.HomeScreen
import com.example.trl.ui.theme.TrlTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            TrlTheme {
                HomeScreen()
            }
        }
    }
}