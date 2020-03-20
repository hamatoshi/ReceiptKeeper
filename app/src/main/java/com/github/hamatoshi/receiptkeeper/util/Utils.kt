package com.github.hamatoshi.receiptkeeper.util

import android.widget.ListView
import androidx.appcompat.app.AppCompatDelegate
import com.github.hamatoshi.receiptkeeper.data.ReceiptContent
import com.github.hamatoshi.receiptkeeper.data.TaxType
import java.text.SimpleDateFormat
import java.util.*

const val DATE_PATTERN = "EEE, MMM d, yyyy"
const val TIME_PATTERN = "HH:mm"
const val INITIAL_COMMIT = -1L

fun ListView.setListViewHeightBasedOnChildren() {
    adapter?.let {
        var totalHeight = 0
        for (i in 0 until adapter.count) {
            val listItem = adapter.getView(i, null, this)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }
        val params = layoutParams
        params.height = totalHeight + (dividerHeight * (adapter.count - 1))
        layoutParams = params
    }
}

fun MutableList<ReceiptContent>.totalTaxPriceTaxOnEach(): Int {
    return this.sumBy { it.taxPrice }
}

fun MutableList<ReceiptContent>.totalTaxPriceTaxOnTotal(): Int {
    val taxGroupLists = this.groupBy { it.tax }
    var result = 0
    for (list in taxGroupLists) {
        result += list.value.filter { it.taxType == TaxType.TAX_EXCLUDED }
            .sumBy { it.priceWithoutTax } *  list.key / 100
        result += list.value.filter { it.taxType == TaxType.TAX_INCLUDED }
            .sumBy { it.taxPrice }
    }
    return result
}

fun MutableList<ReceiptContent>.totalPriceTaxOnEach(): Int {
    return this.sumBy { it.priceWithTax }
}

fun MutableList<ReceiptContent>.totalPriceTaxOnTotal(): Int {
    val taxGroupLists = this.groupBy { it.tax }
    var result = 0
    for (list in taxGroupLists) {
        result += list.value.filter { it.taxType == TaxType.TAX_EXCLUDED }
            .sumBy { it.priceWithoutTax } * (100 + list.key) / 100
        result += list.value.filter { it.taxType == TaxType.TAX_INCLUDED }
            .sumBy { it.priceWithTax }
        result += list.value.filter { it.taxType == TaxType.NO_TAX }
            .sumBy { it.price }
    }
    return result
}

object DateUtils {
    fun millsToString(mills: Long, pattern: String): String {
        val date = Date(mills)
        return SimpleDateFormat(pattern, Locale.US).format(date)
    }

    fun millsOfFrom(mills: Long): Long {
        val date = Date(mills)
        val calendar = Calendar.getInstance().apply { time = date }.apply {
            set(Calendar.DATE, getActualMinimum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }
    fun millsOfTo(mills: Long): Long {
        val date = Date(mills)
        val calendar = Calendar.getInstance().apply { time = date }.apply {
            add(Calendar.MONTH, 1)
            set(Calendar.DATE, getActualMinimum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis - 1
    }

    fun stringToDate(text: String, pattern: String): Date? {
        val df = SimpleDateFormat(pattern, Locale.US)
        return df.parse(text)
    }

    fun stringToString(inputText: String, inputPattern: String, outputPattern: String): String {
        val date = stringToDate(inputText, inputPattern) ?: return "dd"
        return SimpleDateFormat(outputPattern, Locale.US).format(date)
    }
}

object SettingsUtils {
   fun applyTheme(theme: Int?) {
        val mode = when (theme) {
            1 -> AppCompatDelegate.MODE_NIGHT_NO
            2 -> AppCompatDelegate.MODE_NIGHT_YES
            3 -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> AppCompatDelegate.getDefaultNightMode()
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}