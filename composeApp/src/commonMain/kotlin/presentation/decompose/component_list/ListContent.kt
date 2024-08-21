package presentation.decompose.component_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import data.Event
import data.EventsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.components.EventCard

@[Composable OptIn(ExperimentalFoundationApi::class)]
fun ListContent(
    component: ListComponent,
    setOnFabClick: (() -> Unit) -> Unit
) {
    val scope = rememberCoroutineScope()
//    val events by remember { mutableStateOf(Events.startEvents.toMutableStateList()) }
    val listState = rememberLazyListState()
    val onFabClick: () -> Unit = {
        EventsRepository.addEvent()
        scope.launch {
            delay(100)
            listState.animateScrollToItem(0)
        }
    }
    LaunchedEffect(Unit) {
        setOnFabClick(onFabClick)
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        items(
            items = EventsRepository.events.value,
            key = { item: Event -> item.hashCode() }
        ) {
            EventCard(
                event = it,
                modifier = Modifier.animateItemPlacement(),
                onClick = {
                    component.navigateToEvent(event = it)
                }
            )
        }
    }
}