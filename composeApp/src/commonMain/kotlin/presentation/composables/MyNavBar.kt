package presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import presentation.decompose.RootComponent

@Composable
fun MyNavBar(
    currentScreen: RootComponent.Child,
    toList : () -> Unit,
    toSettings : () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = null
                )
            },
            label = { Text("Events") },
            selected = currentScreen is RootComponent.Child.ListChild,
            onClick = toList
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )
            },
            label = { Text("Settings") },
            selected = currentScreen is RootComponent.Child.CharacterListChild,
            onClick = toSettings
        )
    }
}