/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package org.bandev.buddhaquotes.core

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat.getColor
import androidx.preference.PreferenceManager
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.items.Quote

/**
 * Sets navigation bar colour based off android version
 */

@Suppress("DEPRECATION")
fun Window.setNavigationBarColourDefault() {
    if (!inDarkMode(this.context)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, // value
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS // mask
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        this.navigationBarColor = getColor(this.context, R.color.background)
    }
}

@Suppress("DEPRECATION")
fun Window.setNavigationBarColourMain() {
    if (inDarkMode(this.context)) {
        this.navigationBarColor = getColor(this.context, R.color.abbBackgroundColor)
    } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, // value
                WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS // mask
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.navigationBarColor = Color.WHITE
        } else {
            this.navigationBarColor = Color.GRAY
        }
    }
}

fun Window.setNavigationColour() {
    if (inDarkMode(context)) {
        navigationBarColor = getColor(this.context, R.color.abbBackgroundColor)
    }
}

fun inDarkMode(context: Context): Boolean {
    return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> false
    }
}

@Suppress("DEPRECATION")
fun Window.setDarkStatusIcons() {
    if (!inDarkMode(this.context)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, // value
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS // mask
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}

/**
 * Sets activity's theme based off setting from preferences
 */

fun Context.setAccentColour() {
    val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

    this.setTheme(
        when (sharedPrefs.getString("accent_color", "original")) {
            "blue" -> R.style.AppTheme_Blue
            "green" -> R.style.AppTheme_Green
            "orange" -> R.style.AppTheme_Orange
            "yellow" -> R.style.AppTheme_Yellow
            "teal" -> R.style.AppTheme_Teal
            "violet" -> R.style.AppTheme_Violet
            "pink" -> R.style.AppTheme_Pink
            "lightBlue" -> R.style.AppTheme_LightBlue
            "red" -> R.style.AppTheme_Red
            "lime" -> R.style.AppTheme_Lime
            "crimson" -> R.style.AppTheme_Crimson
            else -> R.style.AppTheme_Original
        }
    )
}

/**
 * Gets the current colour and returns it as a string
 * @param [context] context of activity (Context)
 * @return The name of the colour (String)
 */

fun getAccentColourAsString(context: Context): String {
    val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
    return sharedPrefs.getString("accent_color", "original").toString()
}

private fun Context.resolveThemeAttr(@AttrRes attrRes: Int): TypedValue {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue
}

@ColorInt
fun Context.resolveColorAttr(@AttrRes colorAttr: Int): Int {
    val resolvedAttr = resolveThemeAttr(colorAttr)
    val colorRes = if (resolvedAttr.resourceId != 0) resolvedAttr.resourceId else resolvedAttr.data
    return getColor(this, colorRes)
}

fun Context.shareQuote(quote: Quote) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            getString(quote.resource) + "\n\n" + getString(R.string.attribution_buddha)
        )
        type = "text/plain"
    }
    startActivity(Intent.createChooser(sendIntent, null))
}

fun List<Any>.find(item: Any): Int {
    for (i in 0..this.size) {
        if (this[i] == item) return i
    }
    return -1
}
