package presentation.decompose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.composables.CurrencyListCard

@Composable
fun CurrencyListContent(component: CurrencyListComponent) {

    val screenState by component.screenState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = screenState.searchText,
            onValueChange = component::searchByName,
            modifier = Modifier.height(50.dp)
        )
        LazyColumn(
            modifier = Modifier.weight(9f)
        ) {
            items(
                items = screenState.sortedData ?: emptyList()
            ) {
                CurrencyListCard(
                    currency = it,
                    onClick = { component.onCurrencyClick(it) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            val size = 40.dp
            when (screenState.loadingStatus) {
                CurrencyListScreenState.LoadingStatus.Idle -> {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "refresh",
                        modifier = Modifier
                            .size(size)
                            .clickable {
                                component.refreshCurrencies()
                            }
                    )
                }

                CurrencyListScreenState.LoadingStatus.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(size)
                    )
                }
            }
        }
    }

}