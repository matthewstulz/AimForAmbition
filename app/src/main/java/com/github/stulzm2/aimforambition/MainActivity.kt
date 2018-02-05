package com.github.stulzm2.aimforambition

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.github.stulzm2.aimforambition.adapter.GoalAdapter
import com.github.stulzm2.aimforambition.goals.DialogHandler
import com.github.stulzm2.aimforambition.goals.GoalActivity
import com.github.stulzm2.aimforambition.models.Goal

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<GoalAdapter.ViewHolder>? = null
    var listGoals: List<Goal> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initOperations()
    }

    private fun initViews() {
        collapsing_toolbar.title = "AimForAmbition"
        collapsing_toolbar.setContentScrimColor(Color.parseColor("#3F51B5"))
        setSupportActionBar(toolbar)

        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        adapter = GoalAdapter(goalList = listGoals)
        recycler_view.adapter = adapter
    }

    private fun initOperations() {
        fab_goal_intent.setOnClickListener {
            val intent = Intent(this, GoalActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
