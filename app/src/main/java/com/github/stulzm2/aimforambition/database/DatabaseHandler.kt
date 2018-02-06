package com.github.stulzm2.aimforambition.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.github.stulzm2.aimforambition.models.Goal
import java.util.ArrayList

/**
 * Created by matthewstulz on 2/5/18.
 */

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DatabaseHandler.DB_NAME, null, DatabaseHandler.DB_VERSION) {

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "MyGoals"
        private val TABLE_NAME = "Goals"
        private val COLUMN_ID = "Id"
        private val COLUMN_TITLE = "Title"
        private val COLUMN_DESCRIPTION = "Description" }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT)"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addTask(goal: Goal): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, goal.title)
        values.put(COLUMN_DESCRIPTION, goal.description)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    fun getTask(_id: Int): Goal {
        val goals = Goal()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $COLUMN_ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        goals.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID)))
        goals.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
        goals.description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
        cursor.close()
        return goals
    }

    fun task(): List<Goal> {
        val taskList = ArrayList<Goal>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val goals = Goal()
                    goals.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID)))
                    goals.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                    goals.description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                    taskList.add(goals)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return taskList
    }

    fun updateTask(goal: Goal): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, goal.title)
        values.put(COLUMN_DESCRIPTION, goal.description)
        val _success = db.update(TABLE_NAME, values, COLUMN_ID + "=?", arrayOf(goal.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteTask(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, COLUMN_ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteAllTasks(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
}