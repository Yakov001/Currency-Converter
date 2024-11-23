package com.resonanse

import App
import RootComponentImpl
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.retainedComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val rootComponent = retainedComponent { componentContext ->
            RootComponentImpl(
                componentContext = componentContext
            )
        }

        setContent {
            App(rootComponent = rootComponent)
        }
    }
}