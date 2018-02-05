package com.github.stulzm2.aimforambition.adapter

import android.app.AlertDialog
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.github.stulzm2.aimforambition.MainActivity
import com.github.stulzm2.aimforambition.R
import com.github.stulzm2.aimforambition.R.id.card_goal_text
import com.github.stulzm2.aimforambition.models.Goal

/**
 * Created by matthewstulz on 2/4/18.
 */
class GoalAdapter(private val context: Context, goalList: List<Goal>) : RecyclerView.Adapter<GoalAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemTitle: TextView

        init {
            itemTitle = itemView.findViewById(R.id.card_goal_text)

            itemView.setOnClickListener { v: View ->
                var position: Int = getAdapterPosition()

                displayAlert(v, position)

//                Snackbar.make(v, "Click detected on item $position",
//                        Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }

    fun displayAlert(view: View, position: Int) {
        val alert = AlertDialog.Builder(context)
        with(alert) {
            setTitle("Clicked on item $position")
            setMessage("Goal text")

            setPositiveButton("OK") {
                dialog, _ ->
                dialog.dismiss()
                Snackbar.make(view, "OK button was pressed", Snackbar.LENGTH_SHORT).show()
            }

            setNegativeButton("NO") {
                dialog, _ ->
                dialog.dismiss()
                Snackbar.make(view, "NO button was pressed", Snackbar.LENGTH_SHORT).show()
            }
        }

        val dialog = alert.create()
        dialog.show()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_goal, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    private val titles = arrayOf("Goal One Goal One Goal One Goal One Goal One Goal One Goal One Goal One",
            "Goal Two", "Goal Three", "Goal Four",
            "Goal Five", "Goal Six", "Goal Seven",
            "Goal Eight")
}