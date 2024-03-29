package com.rob729.newsfeed.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.rob729.newsfeed.R

object CommonUtils {

    fun getImageRequestModel(context: Context, imageUrl: String, crossFadeDuration: Int = 200) =
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .crossfade(crossFadeDuration)
            .networkCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .build()

    fun openCustomTab(context: Context, url: String) {
        if (url.isBlank()) {
            return
        }
        val builder = CustomTabsIntent.Builder().apply {
            setShowTitle(true)
            setInstantAppsEnabled(true)
        }
        val params = CustomTabColorSchemeParams.Builder()
            .setNavigationBarColor(ContextCompat.getColor(context, R.color.black))
            .setToolbarColor(ContextCompat.getColor(context, R.color.status_bar))
            .build()
        builder.setDefaultColorSchemeParams(params)
        val customBuilder = builder.build()
        customBuilder.intent.setPackage(Constants.CHROME_PACKAGE_NAME)
        customBuilder.launchUrl(
            context,
            Uri.parse(url)
        )
    }
}
