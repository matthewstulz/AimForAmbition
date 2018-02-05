package com.github.stulzm2.aimforambition.goals

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import com.github.stulzm2.aimforambition.R
import kotlinx.android.synthetic.main.activity_goal.*
import kotlinx.android.synthetic.main.card_goal.*

/**
 * Created by matthewstulz on 2/4/18.
 */
class GoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)
        collapsing_toolbar_goal.title = "New Goal"
        isChecked()

    }

    fun isChecked() {
        simpleswitch_deadline.setOnCheckedChangeListener { _ , isChecked ->
            if (isChecked) {
                button_dialog.visibility = View.VISIBLE
            } else {
                button_dialog.visibility = View.INVISIBLE
            }
        }
    }

    fun showDialog(view: View) {
        var dh = DialogHandler()
        dh.show(supportFragmentManager,"date_picker")
    }
}