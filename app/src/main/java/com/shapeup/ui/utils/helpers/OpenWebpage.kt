package com.shapeup.ui.utils.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat

fun openWebpage(context: Context, url: String) {
    val openURL = Intent(Intent.ACTION_VIEW)

    openURL.data = Uri.parse(url)
    ContextCompat.startActivity(context, openURL, null )
}