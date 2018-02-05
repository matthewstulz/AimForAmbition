package com.github.stulzm2.aimforambition.goals

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.stulzm2.aimforambition.R
import kotlinx.android.synthetic.main.activity_goal.*

/**
 * Created by matthewstulz on 2/4/18.
 */
class GoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        collapsing_toolbar_goal.title = "New Goal"
    }
}