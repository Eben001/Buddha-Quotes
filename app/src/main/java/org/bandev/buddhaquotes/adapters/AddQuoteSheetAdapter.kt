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

package org.bandev.buddhaquotes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.custom.AddQuoteSheet
import org.bandev.buddhaquotes.databinding.AddQuoteSheetItemBinding
import org.bandev.buddhaquotes.items.Quote

class AddQuoteSheetAdapter(
    private val list: List<Quote>,
    private val listener: AddQuoteSheet.Listener,
) :
    RecyclerView.Adapter<AddQuoteSheetAdapter.ViewHolder>() {

    class ViewHolder(binding: AddQuoteSheetItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.textView
        val root: RelativeLayout = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AddQuoteSheetItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val quote = list[position]
        with(viewHolder) {
            textView.text = textView.context.getText(quote.resource)
            root.setOnClickListener { listener.select(quote) }
        }
    }

    override fun getItemCount(): Int = list.size

}