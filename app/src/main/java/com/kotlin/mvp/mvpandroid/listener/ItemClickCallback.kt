package com.kotlin.mvp.mvpandroid.listener

/**
* @author by hafiq on 10/12/2017.
*/

interface ItemClickCallback<in Item> {

    fun onItemClick(item: Item)
}
