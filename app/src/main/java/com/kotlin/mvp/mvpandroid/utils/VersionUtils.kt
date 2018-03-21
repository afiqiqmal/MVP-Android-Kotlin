package com.kotlin.mvp.mvpandroid.utils

/**
 * @author : hafiq on 10/10/2016.
 */

class VersionUtils(private val version: String?) : Comparable<VersionUtils> {

    fun get(): String? {
        return this.version
    }

    init {
        if (version == null)
            throw IllegalArgumentException("Version can not be null")
        if (!version.matches("[0-9]+(\\.[0-9]+)*".toRegex()))
            throw IllegalArgumentException("Invalid version format")
    }

    override fun compareTo(other: VersionUtils): Int {
        val curStr = this.get()?.split("\\.".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        val nextStr = other.get()?.split("\\.".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        val length = Math.max(curStr!!.size, nextStr!!.size)
        for (i in 0 until length) {

            val currentVer = if (i < curStr.size) Integer.parseInt(curStr[i]) else 0
            val nextVersion = if (i < nextStr.size) Integer.parseInt(nextStr[i]) else 0

            if (currentVer < nextVersion)
                return -1
            if (currentVer > nextVersion)
                return 1
        }
        return 0
    }

    override fun equals(other: Any?): Boolean {
        return this === other || other != null && this.javaClass == other.javaClass && this.compareTo((other as VersionUtils?)!!) == 0
    }

}
