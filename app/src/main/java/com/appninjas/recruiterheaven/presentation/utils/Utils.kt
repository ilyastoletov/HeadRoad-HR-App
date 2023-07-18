package com.appninjas.recruiterheaven.presentation.utils

import android.view.View

object Utils {

    fun View.setInvisible() {
        this.visibility = View.GONE
    }

    fun View.setVisible() {
        this.visibility = View.VISIBLE
    }

}