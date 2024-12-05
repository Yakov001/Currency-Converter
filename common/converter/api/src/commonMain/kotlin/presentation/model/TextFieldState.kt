package presentation.model

import kotlin.math.ceil
import kotlin.math.floor

data class TextFieldState(
    val amount : Double = 0.0,
    val caretPos: Int = 1
) {
    val amountText: String
        get() {
            // instead of 1.0 return 1 (prettier without decimal)
            return if (floor(amount) == ceil(amount))
                amount.toInt().toString()
            else
                amount.toString()
        }
}
