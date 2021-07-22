package com.hazizah.simplepagination.presentation.adapter

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

object PriceFormatter {
    fun format(input: String): String {
        return "Rp ${thousandGrouping(BigDecimal(input))}"
    }

    private fun thousandGrouping(input: BigDecimal): String {
        val symbols = DecimalFormatSymbols()
        symbols.decimalSeparator = ','
        symbols.groupingSeparator = '.'

        val formatter = DecimalFormat.getNumberInstance() as DecimalFormat
        formatter.decimalFormatSymbols = symbols

        return formatter.format(input)
    }

}
