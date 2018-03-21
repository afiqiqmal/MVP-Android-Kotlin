package com.kotlin.mvp.mvpandroid.utils

import android.support.annotation.AnimRes
import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationUtils
import com.kotlin.mvp.mvpandroid.R

/**
 * @author : hafiq on 02/10/2017.
 */

object RecyclerViewUtils {

    fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_up)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    fun runLayoutAnimation(recyclerView: RecyclerView, @AnimRes res: Int) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, res)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}
