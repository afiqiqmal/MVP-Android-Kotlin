package com.java.mvp.factory.model

/**
 * Created by hafiq on 19/12/2017.
 */

class Apps {
    val id: Int = 0
    val application_name: String? = null
    val application_type: String? = null
    val version: String? = null
    val level: String? = null
    val os: String? = null
    val app_path: String? = null


    override fun toString(): String {
        return "Apps(id=$id, application_name=$application_name, application_type=$application_type, version=$version, level=$level, os=$os, app_path=$app_path)"
    }

}
