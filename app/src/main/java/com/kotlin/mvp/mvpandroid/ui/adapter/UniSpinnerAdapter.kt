package com.kotlin.mvp.mvpandroid.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import uk.co.chrisjenx.calligraphy.TypefaceUtils

/**
 * @author : hafiq on 26/10/2017.
 */

class UniSpinnerAdapter : ArrayAdapter<String> {
    private val font = TypefaceUtils.load(context.assets, "fonts/circularstd-medium.ttf")

    constructor(context: Context, resource: Int, items: List<String>) : super(context, resource, items) {}

    constructor(context: Context, resource: Int, items: Array<String>) : super(context, resource, items) {}

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.typeface = font
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.typeface = font
        return view
    }
}