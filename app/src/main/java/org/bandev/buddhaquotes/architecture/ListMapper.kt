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

package org.bandev.buddhaquotes.architecture

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat.getColor
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.items.List
import org.bandev.buddhaquotes.items.ListIcon

/**
 * Convert db list classes to more friendly
 * UI list classes. Also manage a list of
 * icons for each list.
 *
 * @author Jack Devey
 */

class ListMapper(val application: Application) {

    private val context: Context = application.applicationContext
    val listIcons: MutableList<ListIcon> = mutableListOf()

    init {
        listIcons.add(
            ListIcon(
                0,
                R.drawable.ic_list_icon_heart,
                getColor(context, R.color.colorAccent)
            )
        )
        listIcons.add(
            ListIcon(
                1,
                R.drawable.ic_list_icon_list,
                getColor(context, R.color.blueAccent)
            )
        )
        listIcons.add(
            ListIcon(
                2,
                R.drawable.ic_list_icon_edu,
                getColor(context, R.color.greenAccent)
            )
        )
        listIcons.add(
            ListIcon(
                3,
                R.drawable.ic_list_icon_history,
                getColor(context, R.color.yellowAccent)
            )
        )
        listIcons.add(
            ListIcon(
                4,
                R.drawable.ic_list_icon_interests,
                getColor(context, R.color.tealAccent)
            )
        )
        listIcons.add(
            ListIcon(
                5,
                R.drawable.ic_list_icon_quote,
                getColor(context, R.color.violetAccent)
            )
        )
        listIcons.add(
            ListIcon(
                6,
                R.drawable.ic_list_icon_symbols,
                getColor(context, R.color.orangeAccent)
            )
        )
        listIcons.add(
            ListIcon(
                7,
                R.drawable.ic_list_icon_thumb_up,
                getColor(context, R.color.lightBlueAccent)
            )
        )
        listIcons.add(
            ListIcon(
                8,
                R.drawable.ic_list_icon_trophy,
                getColor(context, R.color.bandev)
            )
        )
    }

    /** Find a list and nicefy it for the UI */
    suspend fun convert(list: Db.List1, repo: Repository.ListQuotes): List =
        List(list.id, list.title, repo.count(list.id), list.system, associate(list.icon))

    /** Find all lists and nicefy it for the UI */
    suspend fun convertAll(
        lists: MutableList<Db.List1>,
        repo: Repository.ListQuotes
    ): MutableList<List> {
        val newList = mutableListOf<List>()
        for (list in lists) newList.add(convert(list, repo))
        return newList
    }

    /** Find a list's icon */
    private fun associate(id: Int): ListIcon = listIcons[id]
}