package com.example.capstonedesign.view

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter

@BindingAdapter("layoutMarginBottom")
fun setLayoutMarginTop(view: View, dimen: Float) {
    (view.layoutParams as ViewGroup.MarginLayoutParams).let {
        it.bottomMargin = dimen.toInt()
        view.layoutParams = it
    }
}