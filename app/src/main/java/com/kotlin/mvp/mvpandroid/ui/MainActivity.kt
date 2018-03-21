package com.kotlin.mvp.mvpandroid.ui

import android.os.Bundle

import com.kotlin.mvp.mvpandroid.R
import com.kotlin.mvp.mvpandroid.ui.common.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent().inject(this)
    }
}
