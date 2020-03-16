package com.github.hamatoshi.receiptkeeper.ui.input

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.github.hamatoshi.receiptkeeper.util.DATE_PATTERN
import com.github.hamatoshi.receiptkeeper.util.DateUtils
import com.github.hamatoshi.receiptkeeper.util.TIME_PATTERN
import java.util.*

class TimePickerFragment(private val textView: TextView): DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = DateUtils.stringToDate(textView.text.toString(), TIME_PATTERN)
        val calendar = Calendar.getInstance().apply {
            time = date ?: return@apply
        }
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(requireActivity(), 0,this, hour, minute, true)
    }

    override fun onTimeSet(timePicker: TimePicker?, hour: Int, minute: Int) {
        val selectedTime = Calendar.getInstance()
        selectedTime.set(0, 0, 0, hour, minute)
        val selectedTimeString = DateUtils.millsToString(selectedTime.timeInMillis, TIME_PATTERN)
        textView.text = selectedTimeString
    }
}

class DatePickerFragment(private val textView: TextView): DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = DateUtils.stringToDate(textView.text.toString(), DATE_PATTERN)
        val calendar = Calendar.getInstance().apply {
            time = date ?: return@apply
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.set(year, month, day)
        val selectedDateString =
            DateUtils.millsToString(selectedCalendar.timeInMillis, DATE_PATTERN)
        textView.text = selectedDateString
    }
}