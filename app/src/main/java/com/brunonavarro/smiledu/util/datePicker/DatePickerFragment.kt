package com.brunonavarro.smiledu.util.datePicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(val listener: (day: Int, month: Int, year: Int) -> Unit)
    : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = GregorianCalendar.getInstance(Locale.getDefault())
        calendar.time = Date()
        calendar.timeZone = TimeZone.getDefault()

        val day = calendar.get(GregorianCalendar.DAY_OF_MONTH)
        val month = calendar.get(GregorianCalendar.MONTH)
        val year = calendar.get(GregorianCalendar.YEAR)

        val picker = DatePickerDialog(activity as Context, this, year, month, day)
        picker.updateDate(year, month, day)
        picker.datePicker.minDate = calendar.timeInMillis
        return picker
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        listener(p3, p2, p1)
    }

}