/*
 * Copyright 2019 Florian Schuster.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tailoredapps.androidutil.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * Intent util creators.
 */
object IntentUtil {
    fun web(url: String): Intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )

    fun mail(mail: String): Intent = Intent(
        Intent.ACTION_SENDTO,
        Uri.parse("mailto:$mail")
    )

    fun call(number: String): Intent = Intent(
        Intent.ACTION_DIAL,
        Uri.parse("tel:$number")
    )

    fun maps(location: String): Intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("http://maps.google.co.in/maps?q=$location")
    )

    fun playstore(context: Context): Intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://play.google.com/store/apps/details?id=${context.applicationContext.packageName}")
    )

    fun appSettings(context: Context): Intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )

    fun notificationSettings(context: Context): Intent = Intent(
        "android.settings.APP_NOTIFICATION_SETTINGS"
    ).apply {
        putExtra("app_package", context.packageName)
        putExtra("app_uid", context.applicationInfo.uid)
        putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
    }
}