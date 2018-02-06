package com.github.stulzm2.aimforambition.goals

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast

/**
 * Created by matthewstulz on 2/5/18.
 */
class DateSettings : DatePickerDialog.OnDateSetListener {

    var con: Context

    constructor(con: Context) {
        this.con = con
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val newMonth = month + 1
        Toast.makeText(con,"Selected Date : "+newMonth+" / "+dayOfMonth+" / "+year, Toast.LENGTH_LONG).show()

    }
}