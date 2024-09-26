package presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.ktor.dto.Currency

@Composable
fun CurrencyListCard(currency: Currency) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(
                text = "${currency.name} ${currency.sign}",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = currency.symbol
            )
            Text(
                text = currency.id.toString()
            )
        }
    }
}