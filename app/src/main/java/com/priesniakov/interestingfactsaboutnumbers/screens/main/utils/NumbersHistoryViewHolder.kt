package com.priesniakov.interestingfactsaboutnumbers.screens.main.utils

import androidx.recyclerview.widget.RecyclerView
import com.priesniakov.interestingfactsaboutnumbers.data.entity.NumberFactResponse
import com.priesniakov.interestingfactsaboutnumbers.databinding.ItemNumbersHistoryBinding
import kotlin.math.roundToLong

class NumbersHistoryViewHolder(
    private val binding: ItemNumbersHistoryBinding,
    private val onClickAction: (item: NumberFactResponse) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NumberFactResponse) = with(binding) {
        number.text = item.number.roundToLong().toString()
        fact.text = item.text

        root.setOnClickListener {
            onClickAction(item)
        }
    }
}