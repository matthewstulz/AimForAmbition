package com.github.stulzm2.aimforambition.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.stulzm2.aimforambition.R
import com.github.stulzm2.aimforambition.goals.GoalActivity
import com.github.stulzm2.aimforambition.models.Goal
import java.util.ArrayList

/**
 * Created by matthewstulz on 2/4/18.
 */
class GoalAdapter(goalList: List<Goal>, private var context: Context) : RecyclerView.Adapter<GoalAdapter.TaskViewHolder>() {

    private var goalList: List<Goal> = ArrayList()
    init {
        this.goalList = goalList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_goal, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val goals = goalList[position]
        holder.title.text = goals.title
        holder.description.text = goals.description
        holder.date.text = goals.date
        holder.priority.text = goals.priority

        holder.itemView.setOnClickListener {
            val i = Intent(context, GoalActivity::class.java)
            i.putExtra("Mode", "E")
            i.putExtra("Id", goals.id)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return goalList.size
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.card_goal_text) as TextView
        var description: TextView = view.findViewById(R.id.card_goal_description) as TextView
        var date: TextView = view.findViewById(R.id.card_goal_deadline) as TextView
        var priority: TextView = view.findViewById(R.id.card_goal_priority) as TextView
    }
}