package com.github.stulzm2.aimforambition

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
    }

    private fun initViews() {
        supportActionBar?.title = "About"
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
