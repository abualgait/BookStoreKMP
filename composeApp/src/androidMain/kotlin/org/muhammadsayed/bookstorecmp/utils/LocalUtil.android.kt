package org.muhammadsayed.bookstorecmp.utils

import android.content.Intent
import android.content.res.Configuration
import java.util.Locale

actual fun changeLocale(locale: String) {
    val context = ContextUtils.context
    val mLocale = Locale(locale)
    Locale.setDefault(mLocale)
    val resources = context.resources
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(mLocale)
    resources.updateConfiguration(configuration, resources.displayMetrics)

    //restart the app
    val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}

