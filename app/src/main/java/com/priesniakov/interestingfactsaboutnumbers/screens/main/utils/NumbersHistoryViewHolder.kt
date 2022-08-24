package com.priesniakov.interestingfactsaboutnumbers.screens.main.utils

import androidx.recyclerview.widget.RecyclerView
import com.priesniakov.interestingfactsaboutnumbers.data.entity.NumberFactResponse
import com.priesniakov.interestingfactsaboutnumbers.databinding.ItemNumbersHistoryBinding

class NumbersHistoryViewHolder(private val binding: ItemNumbersHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NumberFactResponse) = with(binding) {
        number.text = item.number.toString()
        fact.text = item.text
    }
}