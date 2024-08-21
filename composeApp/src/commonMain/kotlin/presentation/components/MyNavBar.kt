package presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MyNavBar(
    selectedItemIndex : Int,
    onItemSelected : (Int) -> Unit
) {
    NavigationBar(

    ) {
        repeat(2) { index ->
            val icon = when (index) {
                0 -> Icons.AutoMirrored.Filled.List
                1 -> Icons.Default.Settings
                else -> Icons.Default.Favorite
            }
            NavigationBarItem(
                icon = { Icon(imageVector = icon, contentDescription = null) },
                label = { Text("amogus") },
                selected = selectedItemIndex == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}