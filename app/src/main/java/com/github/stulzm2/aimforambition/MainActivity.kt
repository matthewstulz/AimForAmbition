package com.github.stulzm2.aimforambition

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.github.stulzm2.aimforambition.adapter.GoalAdapter
import com.github.stulzm2.aimforambition.database.DatabaseHandler
import com.github.stulzm2.aimforambition.goals.GoalActivity
import com.github.stulzm2.aimforambition.models.Goal
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by matthewstulz on 2/4/18.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var goalRecyclerAdapter: GoalAdapter
    private lateinit var dbHandler: DatabaseHandler
    private var listGoals: List<Goal> = ArrayList()
    private var originalOrder: List<Goal> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var sortGoals: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initOperations()
        snackBarCheck()
    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        listGoals = dbHandler.goal()
        goalRecyclerAdapter = GoalAdapter(goalList = listGoals, context = applicationContext)
        recycler_view.adapter = goalRecyclerAdapter
        emptyViewCheck()
        originalOrder = listGoals
    }

    private fun initViews() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        goalRecyclerAdapter = GoalAdapter(goalList = listGoals, context = applicationContext)
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

    private fun emptyViewCheck() {
        if (recycler_view.adapter.itemCount == 0) {
            recycler_view.visibility = View.INVISIBLE
            tv_no_data.visibility = View.VISIBLE
        } else {
            recycler_view.visibility = View.VISIBLE
            tv_no_data.visibility = View.GONE
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
        when (id) {
            R.id.action_delete -> {
                val dialog = android.support.v7.app.AlertDialog.Builder(this).setTitle("DANGER ZONE!").setMessage("Click 'YES' to delete all goals")
                        .setPositiveButton("YES", { dialog, _ ->
                            dbHandler.deleteAllGoals()
                            initDB()
                            dialog.dismiss()
                        })
                        .setNegativeButton("NO", { dialog, _ ->
                            dialog.dismiss()
                        })
                dialog.show()
                return true
            }

            R.id.sort_settings -> {
                val singleChoiceItems = resources.getStringArray(R.array.dialog_single_choice_sort_array)
                val itemSelected = 0
                AlertDialog.Builder(this)
                        .setTitle("Sort by")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected) { _ , selectedIndex ->
                            when (selectedIndex) {
                                0 -> { sortGoals = singleChoiceItems[0]
                                    listGoals = originalOrder
                                }
                                1 -> { sortGoals = singleChoiceItems[1]
                                    listGoals = listGoals.sortedWith(compareBy({ it.date }))
                                }
                                2 -> { sortGoals = singleChoiceItems[2]
                                    listGoals = listGoals.sortedWith(compareBy({ it.priority }))
                                }
                            }
                        }
                        .setPositiveButton("Ok", { _ : DialogInterface, _ : Int ->
                            goalRecyclerAdapter = GoalAdapter(goalList = listGoals, context = applicationContext)
                            recycler_view.adapter = goalRecyclerAdapter
                        })
                        .setNegativeButton("Cancel", null)
                        .show()
            }

            R.id.about_settings -> {
                val i = Intent(applicationContext, AboutActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        initDB()
    }
}
