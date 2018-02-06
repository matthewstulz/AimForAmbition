package com.github.stulzm2.aimforambition.goals

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.github.stulzm2.aimforambition.R
import com.github.stulzm2.aimforambition.database.DatabaseHandler
import com.github.stulzm2.aimforambition.models.Goal
import kotlinx.android.synthetic.main.activity_goal.*

/**
 * Created by matthewstulz on 2/4/18.
 */
class GoalActivity : AppCompatActivity() {

    var dbHandler: DatabaseHandler? = null
    var isEditMode = false

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

    fun showDialog(view: View) {
        var dh = DialogHandler()
        dh.show(supportFragmentManager,"date_picker")
    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        button_delete_goal.visibility = View.INVISIBLE
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
