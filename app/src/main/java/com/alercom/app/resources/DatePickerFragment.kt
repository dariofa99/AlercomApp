package com.alercom.app.resources

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(val listener : (day:Int, month:Int, year:Int) -> Unit): DialogFragment(),
DatePickerDialog.OnDateSetListener{
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
       listener(dayOfMonth,month,year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar : Calendar = Calendar.getInstance()
        val day:Int = calendar.get(Calendar.DAY_OF_MONTH)
        val mont:Int = calendar.get(Calendar.MONTH)
        val year:Int = calendar.get(Calendar.YEAR)
        val picker = DatePickerDialog(activity as Context, this,year,(mont),day)
        picker.datePicker.maxDate = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_MONTH, - 8)
        picker.datePicker.minDate = calendar.timeInMillis
        return  picker




    }
}