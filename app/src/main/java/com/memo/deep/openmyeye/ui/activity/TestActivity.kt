package com.memo.deep.openmyeye.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.memo.deep.openmyeye.R

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
    }

    private fun initView() {
    }

    override fun onRestart() {
        super.onRestart()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}
