package presentation.decompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import presentation.composables.CurrencyListCard
import presentation.composables.SearchCurrencyTextField

@Composable
fun CurrencyListContent(component: CurrencyListComponent) {

    val screenState by component.screenState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        SearchCurrencyTextField(
            text = screenState.searchText,
            onTextChange = component::searchByName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .weight(9f)
                .padding(top = 16.dp),
            contentPadding = PaddingValues(
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            ),
        ) {
            items(
                items = screenState.sortedData ?: emptyList()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    CurrencyListCard(
                        currency = it,
                        onClick = { component.onCurrencyClick(it) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}