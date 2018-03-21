package com.kotlin.mvp.mvpandroid.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import java.util.*

/**
 * @author : hafiq on 01/09/2017.
 */

class ViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
    private val mFragmentList: MutableList<Fragment>
    private val mFragmentTitleList: MutableList<String>

    init {
        mFragmentTitleList = ArrayList()
        mFragmentList = ArrayList()
    }

    override fun getItem(index: Int): Fragment {
        return mFragmentList[index]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }


    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    fun deletePage(position: Int) {
        mFragmentList.removeAt(position)
        mFragmentTitleList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }
}