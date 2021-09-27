package com.envious.watchlist.ui.watch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envious.domain.model.WatchItem
import com.envious.watchlist.R
import com.envious.watchlist.databinding.ListItemRowBinding

class WatchListAdapter(private val context: Context) : RecyclerView.Adapter<WatchListAdapter.WatchItemViewHolder>() {
    private var listItem: MutableList<WatchItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListAdapter.WatchItemViewHolder {
        return WatchItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_row, parent, false)
        )
    }

    override fun getItemId(position: Int): Long {
        val item: WatchItem = listItem[position]
        return item.id.toLong()
    }

    fun addData(list: List<WatchItem>) {
        this.listItem.addAll(list)
    }

    fun setList(list: List<WatchItem>) {
        this.listItem.clear()
        this.listItem.addAll(list)
    }

    override fun onBindViewHolder(holderWatchItem: WatchItemViewHolder, position: Int) {
        with(holderWatchItem) {
            binding.textShortName.text = listItem[position].shortName
            binding.textFullName.text = listItem[position].fullName
            binding.textPrice.text = listItem[position].price.toString()
            binding.textChange.text = listItem[position].changePrice24.toString()
        }
    }

    override fun getItemCount(): Int {
        return if (listItem.isNullOrEmpty()) {
            0
        } else {
            listItem.size
        }
    }

    class WatchItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListItemRowBinding.bind(view)
    }
}
