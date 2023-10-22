package com.touchin.prosto.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.touchin.prosto.R

/**
 * Вызывает приложение для почты и предзаполняет поля письма
 * @param subject Тема письма
 * @param body Тело письма
 */
fun sendEmail(
    context: Context,
    email: String,
    subject: String,
    titleForChooser: String = context.getString(R.string.common_email),
    body: String? = null,
    onError: () -> Unit = {}
) {
    val sendIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        body?.let { putExtra(Intent.EXTRA_TEXT, it) }
    }

    try {
        val chooserIntent = Intent.createChooser(sendIntent, titleForChooser).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(chooserIntent)
    } catch (e: ActivityNotFoundException) {
        onError.invoke()
    }
}
