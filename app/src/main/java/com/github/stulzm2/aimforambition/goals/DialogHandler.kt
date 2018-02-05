package com.github.stulzm2.aimforambition.goals

import android.app.DatePickerDialog
import android.app.Dialog
import android.support.v4.app.DialogFragment
import android.os.Bundle
import com.github.stulzm2.aimforambition.R
import java.util.*

/**
 * Created by matthewstulz on 2/5/18.
 */
class DialogHandler : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var calendar: Calendar = Calendar.getInstance()
        var year: Int = calendar.get(Calendar.YEAR)
        var month: Int = calendar.get(Calendar.MONTH)
        var day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        var dialog: DatePickerDialog
        var ds = DateSettings(activity)
        dialog = DatePickerDialog(activity,ds,year,month,day)
        return dialog
    }
}