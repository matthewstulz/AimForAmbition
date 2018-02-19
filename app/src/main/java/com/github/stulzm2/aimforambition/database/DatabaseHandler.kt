package com.github.stulzm2.aimforambition.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.TextUtils
import com.github.stulzm2.aimforambition.models.Goal
import java.util.ArrayList

/**
 * Created by matthewstulz on 2/5/18.
 */
class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DatabaseHandler.DB_NAME, null, DatabaseHandler.DB_VERSION) {

    companion object {
        private val DB_VERSION = 2
        private val DB_NAME = "MyGoals"
        private val TABLE_NAME = "Goals"
        private val COLUMN_ID = "Id"
        private val COLUMN_TITLE = "Title"
        private val COLUMN_DESCRIPTION = "Description"
        private val COLUMN_DATE = "Date"
        private val COLUMN_PRIORITY = "Priority"}

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_DATE TEXT, $COLUMN_PRIORITY TEXT)"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    private fun checkEmpty(string: String): Boolean {
        if (TextUtils.isEmpty(string)) {
            return true
        }
        return false
    }

    fun addGoal(goal: Goal): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        if (checkEmpty(goal.title))
            return false
        values.put(COLUMN_TITLE, goal.title)
        values.put(COLUMN_DESCRIPTION, goal.description)
        values.put(COLUMN_DATE, goal.date)
        values.put(COLUMN_PRIORITY, goal.priority)
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return "$result".toInt() != -1
    }

    fun getGoal(_id: Int): Goal {
        val goal = Goal()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $COLUMN_ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        goal.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID)))
        goal.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
        goal.description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
        goal.date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
        goal.priority = cursor.getString(cursor.getColumnIndex(COLUMN_PRIORITY))
        cursor.close()
        return goal
    }

    fun goal(): List<Goal> {
        val goalList = ArrayList<Goal>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val goal = Goal()
                    goal.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID)))
                    goal.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                    goal.description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                    goal.date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                    goal.priority = cursor.getString(cursor.getColumnIndex(COLUMN_PRIORITY))
                    goalList.add(goal)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return goalList
    }

    fun updateGoal(goal: Goal): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, goal.title)
        values.put(COLUMN_DESCRIPTION, goal.description)
        values.put(COLUMN_DATE, goal.date)
        values.put(COLUMN_PRIORITY, goal.priority)
        val result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", arrayOf(goal.id.toString())).toLong()
        db.close()
        return "$result".toInt() != -1
    }

    fun deleteGoal(_id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, COLUMN_ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return "$result".toInt() != -1
    }

    fun deleteAllGoals(): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return "$result".toInt() != -1
    }
}