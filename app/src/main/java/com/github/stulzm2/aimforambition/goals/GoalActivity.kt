package com.github.stulzm2.aimforambition.goals

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import com.github.stulzm2.aimforambition.R
import com.github.stulzm2.aimforambition.database.DatabaseHandler
import com.github.stulzm2.aimforambition.models.Goal
import kotlinx.android.synthetic.main.activity_goal.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by matthewstulz on 2/4/18.
 */
class GoalActivity : AppCompatActivity() {

    var dbHandler: DatabaseHandler? = null
    var isEditMode = false
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        isChecked()
        initDB()
        initOperations()

    }

    fun isChecked() {
        simpleswitch_deadline.setOnCheckedChangeListener { _ , isChecked ->
            if (isChecked) {
                button_dialog.visibility = View.VISIBLE
            } else {
                button_dialog.visibility = View.GONE
            }
        }
    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        button_delete_goal.visibility = View.GONE
        collapsing_toolbar_goal.title = "New Goal"
        if (intent != null && intent.getStringExtra("Mode") == "E") {
            isEditMode = true
            collapsing_toolbar_goal.title = "Edit Goal"
            val goal: Goal = dbHandler!!.getTask(intent.getIntExtra("Id",0))
            textinput_goal.setText(goal.title)
            textinput_description.setText(goal.description)

            button_delete_goal.visibility = View.VISIBLE
        }
    }

    private fun initOperations() {
        button_add_goal.setOnClickListener({
            var success: Boolean
            if (!isEditMode) {
                val goal = Goal()
                goal.title = textinput_goal.text.toString()
                goal.description = textinput_description.text.toString()

                success = dbHandler?.addTask(goal) as Boolean
            } else {
                val tasks = Goal()
                tasks.id = intent.getIntExtra("Id", 0)
                tasks.title = textinput_goal.text.toString()
                tasks.description = textinput_description.text.toString()

                success = dbHandler?.updateTask(tasks) as Boolean
            }

            if (success)
                finish()
        })

        button_delete_goal.setOnClickListener({
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click 'YES' Delete the Task.")
                    .setPositiveButton("YES", { dialog, i ->
                        val success = dbHandler?.deleteTask(intent.getIntExtra("Id", 0)) as Boolean
                        if (success)
                            finish()
                        dialog.dismiss()
                    })
                    .setNegativeButton("NO", { dialog, i ->
                        dialog.dismiss()
                    })
            dialog.show()
        })

        textview_date!!.text = "--/--/----"

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        button_dialog!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@GoalActivity,
                        dateSetListener,
                        // selects today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // format of time
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.text = sdf.format(cal.getTime())
    }
}
