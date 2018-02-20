package com.github.stulzm2.aimforambition

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_about.*

/**
 * Created by matthewstulz on 2/19/18.
 */
class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
        initOperations()
    }

    private fun initViews() {
        supportActionBar?.title = "About"
    }

    private fun initOperations() {
        button_url_github.setOnClickListener({
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_github)))
            startActivity(intent)
        })

        button_url_canva.setOnClickListener({
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_logo)))
            startActivity(intent)
        })

        button_email.setOnClickListener({
            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse("mailto:mstulz2@gmail.com?subject=&body=")
            intent.data = data
            startActivity(intent)
        })
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
