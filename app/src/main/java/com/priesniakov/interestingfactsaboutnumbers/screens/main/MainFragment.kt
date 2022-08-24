package com.priesniakov.interestingfactsaboutnumbers.screens.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.priesniakov.interestingfactsaboutnumbers.core.base.BaseFragment
import com.priesniakov.interestingfactsaboutnumbers.core.data.ResourceError
import com.priesniakov.interestingfactsaboutnumbers.core.data.ResourceIdle
import com.priesniakov.interestingfactsaboutnumbers.core.data.ResourceLoading
import com.priesniakov.interestingfactsaboutnumbers.core.data.ResourceSuccess
import com.priesniakov.interestingfactsaboutnumbers.data.common.LOGGER_TAG
import com.priesniakov.interestingfactsaboutnumbers.databinding.FragmentFirstBinding
import com.priesniakov.interestingfactsaboutnumbers.screens.main.utils.NumbersHistoryRVAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainFragment : BaseFragment<FragmentFirstBinding>(FragmentFirstBinding::inflate) {

    private val viewModel: MainViewModel by inject()
    private val adapter by lazy { NumbersHistoryRVAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getHistory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupListeners()
        subscribeUI()
    }

    private fun subscribeUI() {
        collectResult(flow = viewModel.numberFlow,
            successAction = { data ->
                Log.d(LOGGER_TAG, data.data.text)
            })

        lifecycleScope.launch {
            viewModel.numbersHistoryFlow.collectLatest {
                adapter.setItems(it)
            }
        }
    }

    private fun setupListeners() = with(binding) {
        getRandomFact.setOnClickListener {
            viewModel.getRandomFact()
        }
        getFact.setOnClickListener {
            val number = editText.text.toString()
            if (number.isNotBlank()) {
                viewModel.getNumberFact(number)
            }
        }
    }

    private fun setupUI() = with(binding) {
        editText.transformationMethod = null
        historyRv.adapter = adapter
    }
}