package com.envious.watchlist.ui.watch

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.envious.domain.model.WatchItem
import com.envious.watchlist.R
import com.envious.watchlist.databinding.ListItemRowBinding

class WatchListAdapter(private val context: Context) : RecyclerView.Adapter<WatchListAdapter.WatchItemViewHolder>() {
    private var listItem: MutableList<WatchItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListAdapter.WatchItemViewHolder {
        val binding: ListItemRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item_row, parent, false)
        return WatchItemViewHolder(binding)
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
        listItem[holderWatchItem.adapterPosition].let {
            holderWatchItem.bindData(it, context)
        }
    }

    override fun getItemCount(): Int {
        return if (listItem.isNullOrEmpty()) {
            0
        } else {
            listItem.size
        }
    }

    class WatchItemViewHolder(private val binding: ListItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(model: WatchItem, context: Context) {
            binding.item = model
        }
    }
}
