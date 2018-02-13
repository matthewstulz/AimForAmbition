package com.github.stulzm2.aimforambition

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.github.stulzm2.aimforambition.adapter.GoalAdapter
import com.github.stulzm2.aimforambition.database.DatabaseHandler
import com.github.stulzm2.aimforambition.goals.GoalActivity
import com.github.stulzm2.aimforambition.models.Goal

import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by matthewstulz on 2/4/18.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var goalRecyclerAdapter: GoalAdapter
    private lateinit var dbHandler: DatabaseHandler
    private var listTasks: List<Goal> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initOperations()
        snackBarCheck()
    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        listTasks = dbHandler.goal()
        goalRecyclerAdapter = GoalAdapter(goalList = listTasks, context = applicationContext)
        recycler_view.adapter = goalRecyclerAdapter
    }

    private fun initViews() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        goalRecyclerAdapter = GoalAdapter(goalList = listTasks, context = applicationContext)
        linearLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayoutManager
    }

    private fun initOperations() {
        fab_goal_intent?.setOnClickListener { _ ->
            val i = Intent(applicationContext, GoalActivity::class.java)
            i.putExtra("Mode", "A")
            startActivity(i)
        }
    }

    private fun snackBarCheck() {
        if (intent.extras != null) {
            if (intent.getStringExtra("deletion") == "success") {
                Snackbar.make(cl_main, "Goal successfully deleted", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_delete) {
            val dialog = android.support.v7.app.AlertDialog.Builder(this).setTitle("DANGER ZONE!").setMessage("Click 'YES' to delete all goals")
                    .setPositiveButton("YES", { dialog, i ->
                        dbHandler!!.deleteAllGoals()
                        initDB()
                        dialog.dismiss()
                    })
                    .setNegativeButton("NO", { dialog, i ->
                        dialog.dismiss()
                    })
            dialog.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        initDB()
    }
}
