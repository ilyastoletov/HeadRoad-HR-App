package com.appninjas.recruiterheaven.presentation.utils

import android.view.View
import com.appninjas.recruiterheaven.presentation.adapter.model.SocialNetwork

object Utils {

    fun View.setInvisible() {
        this.visibility = View.GONE
    }

    fun View.setVisible() {
        this.visibility = View.VISIBLE
    }

}