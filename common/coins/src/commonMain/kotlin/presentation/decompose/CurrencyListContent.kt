package presentation.decompose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.composables.CurrencyListCard

@Composable
fun CurrencyListContent(component: CurrencyListComponent) {

    val currencies by component.currencies.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = currencies
        ) {
            CurrencyListCard(
                currency = it,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}