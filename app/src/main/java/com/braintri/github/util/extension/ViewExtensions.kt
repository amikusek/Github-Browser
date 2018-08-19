package com.braintri.github.util.extension

import android.view.View
import com.jakewharton.rxbinding2.view.RxView

fun View.throttleClicks() = RxView.clicks(this).throttleFirst(200, java.util.concurrent.TimeUnit.MILLISECONDS)

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}
