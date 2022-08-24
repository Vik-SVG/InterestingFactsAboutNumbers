package com.priesniakov.interestingfactsaboutnumbers.screens.main.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.priesniakov.interestingfactsaboutnumbers.data.entity.NumberFactResponse
import com.priesniakov.interestingfactsaboutnumbers.databinding.ItemNumbersHistoryBinding

class NumbersHistoryRVAdapter(private val onClickAction: (item: NumberFactResponse) -> Unit) :
    RecyclerView.Adapter<NumbersHistoryViewHolder>() {

    private val itemList = mutableListOf<NumberFactResponse>()

    fun setItems(items: List<NumberFactResponse>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNumbersHistoryBinding.inflate(inflater, parent, false)
        return NumbersHistoryViewHolder(binding, onClickAction)
    }

    override fun onBindViewHolder(holder: NumbersHistoryViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

}