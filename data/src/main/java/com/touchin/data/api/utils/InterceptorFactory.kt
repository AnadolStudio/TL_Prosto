package com.touchin.data.api.utils

import android.content.Context
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.ironz.binaryprefs.BuildConfig
import com.touchin.data.api.utils.NetworkConstants.APPLICATION_VERSION_HEADER
import okhttp3.Interceptor

class InterceptorFactory(
    private val context: Context,
) {

    companion object {
        private const val APPLICATION_VERSION_FORMAT = "android:%s"
    }

    fun getNonAuthZoneInterceptor(): List<Interceptor> {
        return listOf(
            getErrorInterceptor(),
            getMobileAppHeadersInterceptor(),
            getLoggingInterceptor(),
        )
    }

    private fun getErrorInterceptor(): Interceptor {
        return Interceptor { chain ->
            return@Interceptor chain.proceed(chain.request().newBuilder().build())
        }
    }

    private fun getLoggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .request("Request")
            .response("Response")
            .build()
    }

    private fun getMobileAppHeadersInterceptor(): Interceptor {
        return Interceptor { chain ->
            with(chain.request().newBuilder()) {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                val applicationVersion = String.format(APPLICATION_VERSION_FORMAT, packageInfo.versionName)

                addHeader(APPLICATION_VERSION_HEADER, applicationVersion)

                chain.proceed(build())
            }
        }
    }

}
