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

package org.bandev.buddhaquotes.custom

import android.view.View
import kotlinx.coroutines.*

/**
 * DoubleClickListener extends onClickListener, allowing for the detection of double clicks.
 */

abstract class DoubleClickListener : View.OnClickListener {
    private var job: Job? = null
    private val delay = 400L
    private var lastClickTime: Long = 0
    override fun onClick(view: View?) {
        val clickTime = System.currentTimeMillis()
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) processDoubleClickEvent(view)
        else processSingleClickEvent(view)
        lastClickTime = clickTime
    }

    private fun processSingleClickEvent(view: View?) {
        job = CoroutineScope(Dispatchers.Main).launch {
            delay(delay)
            onSingleClick(view)
        }
    }

    private fun processDoubleClickEvent(view: View?) {
        job?.cancel()
        onDoubleClick(view)
    }

    abstract fun onSingleClick(view: View?)
    abstract fun onDoubleClick(view: View?)

    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300
    }
}
