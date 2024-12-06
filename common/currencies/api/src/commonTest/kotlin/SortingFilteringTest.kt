import domain.model.CurrencyEntity
import kotlinx.datetime.Instant
import presentation.decompose.filteredBySearch
import presentation.decompose.sortedByOptions
import presentation.model.SortOption
import kotlin.test.Test
import kotlin.test.assertEquals

class SortingFilteringTest {

    val dataUnfiltered = listOf(
        CurrencyEntity(
            currencyCode = "USD",
            currencyName = "United States Dollar",
            flagImageUrl = "",
            fromUsd = 1.0,
            fetchTimeInstant = Instant.fromEpochMilliseconds(1)
        ),
        CurrencyEntity(
            currencyCode = "EUR",
            currencyName = "Euro",
            flagImageUrl = "",
            fromUsd = 0.88,
            fetchTimeInstant = Instant.fromEpochMilliseconds(1)
        ),
        CurrencyEntity(
            currencyCode = "RUB",
            currencyName = "Russian Ruble",
            flagImageUrl = "",
            fromUsd = 0.013,
            fetchTimeInstant = Instant.fromEpochMilliseconds(1)
        )
    )

    val dataFilteredByCurrencyCodeAscending = listOf(
        CurrencyEntity(
            currencyCode = "EUR",
            currencyName = "Euro",
            flagImageUrl = "",
            fromUsd = 0.88,
            fetchTimeInstant = Instant.fromEpochMilliseconds(1)
        ),
        CurrencyEntity(
            currencyCode = "RUB",
            currencyName = "Russian Ruble",
            flagImageUrl = "",
            fromUsd = 0.013,
            fetchTimeInstant = Instant.fromEpochMilliseconds(1)
        ),
        CurrencyEntity(
            currencyCode = "USD",
            currencyName = "United States Dollar",
            flagImageUrl = "",
            fromUsd = 1.0,
            fetchTimeInstant = Instant.fromEpochMilliseconds(1)
        )
    )

    val dataFilteredByCurrencyCodeDescending = listOf(
        CurrencyEntity(
            currencyCode = "USD",
            currencyName = "United States Dollar",
            flagImageUrl = "",
            fromUsd = 1.0,
            fetchTimeInstant = Instant.fromEpochMilliseconds(1)
        ),
        CurrencyEntity(
            currencyCode = "RUB",
            currencyName = "Russian Ruble",
            flagImageUrl = "",
            fromUsd = 0.013,
            fetchTimeInstant = Instant.fromEpochMilliseconds(1)
        ),
        CurrencyEntity(
            currencyCode = "EUR",
            currencyName = "Euro",
            flagImageUrl = "",
            fromUsd = 0.88,
            fetchTimeInstant = Instant.fromEpochMilliseconds(1)
        )
    )


    @Test
    fun testSortingFilteringDescending() {
        assertEquals(
            expected = dataFilteredByCurrencyCodeDescending,
            actual = dataUnfiltered.sortedByOptions(sortOption = SortOption.CurrencyCode(ascending = false))
        )
    }

    @Test
    fun testSortingFilteringSeveralTimes() {
        var data : List<CurrencyEntity>? = dataUnfiltered
        data = data.sortedByOptions(sortOption = SortOption.CurrencyCode(ascending = true))
        assertEquals(dataFilteredByCurrencyCodeAscending, data)
        data = data.sortedByOptions(sortOption = SortOption.CurrencyCode(ascending = false))
        assertEquals(dataFilteredByCurrencyCodeDescending, data)
        data = data.sortedByOptions(sortOption = SortOption.CurrencyCode(ascending = true))
        assertEquals(dataFilteredByCurrencyCodeAscending, data)
        data = dataUnfiltered
            .sortedByOptions(SortOption.CurrencyCode(true))
            .sortedByOptions(SortOption.CurrencyCode(true))
        assertEquals(dataFilteredByCurrencyCodeAscending, data)
    }

    @Test
    fun testSortingFiltering() {
        assertEquals(
            expected = dataFilteredByCurrencyCodeAscending,
            actual = dataUnfiltered.sortedByOptions(sortOption = SortOption.CurrencyCode(ascending = true))
        )
    }

    @Test
    fun testSortingFilteringReverseOrder() {
        val dataFilteredByCurrencyCodeDescending = listOf(
            CurrencyEntity(
                currencyCode = "USD",
                currencyName = "United States Dollar",
                flagImageUrl = "",
                fromUsd = 1.0,
                fetchTimeInstant = Instant.fromEpochMilliseconds(1)
            ),
            CurrencyEntity(
                currencyCode = "RUB",
                currencyName = "Russian Ruble",
                flagImageUrl = "",
                fromUsd = 0.013,
                fetchTimeInstant = Instant.fromEpochMilliseconds(1)
            ),
            CurrencyEntity(
                currencyCode = "EUR",
                currencyName = "Euro",
                flagImageUrl = "",
                fromUsd = 0.88,
                fetchTimeInstant = Instant.fromEpochMilliseconds(1)
            )
        )

        assertEquals(
            expected = dataFilteredByCurrencyCodeDescending,
            actual = dataUnfiltered.sortedByOptions(sortOption = SortOption.CurrencyCode(ascending = false))
        )
    }

    @Test
    fun testKotlinSorting() {
        val stringList = listOf("ab", "bc", "cd")
        val sortedList = stringList.sortedBy { it.reversed() }
        println(sortedList.joinToString(" "))
    }
}