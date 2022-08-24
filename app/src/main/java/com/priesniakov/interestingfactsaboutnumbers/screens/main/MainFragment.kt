package com.priesniakov.interestingfactsaboutnumbers.screens.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.priesniakov.interestingfactsaboutnumbers.R
import com.priesniakov.interestingfactsaboutnumbers.core.base.BaseFragment
import com.priesniakov.interestingfactsaboutnumbers.core.data.ResourceError
import com.priesniakov.interestingfactsaboutnumbers.core.data.ResourceIdle
import com.priesniakov.interestingfactsaboutnumbers.core.data.ResourceLoading
import com.priesniakov.interestingfactsaboutnumbers.core.data.ResourceSuccess
import com.priesniakov.interestingfactsaboutnumbers.data.common.LOGGER_TAG
import com.priesniakov.interestingfactsaboutnumbers.data.entity.NumberFactResponse
import com.priesniakov.interestingfactsaboutnumbers.databinding.FragmentFirstBinding
import com.priesniakov.interestingfactsaboutnumbers.screens.main.utils.NumbersHistoryRVAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class MainFragment : BaseFragment<FragmentFirstBinding>(FragmentFirstBinding::inflate) {

    private val viewModel: MainViewModel by inject()
    private val adapter by lazy { NumbersHistoryRVAdapter(::navigateToDetailsScreen) }

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

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }

    private fun subscribeUI() {
        collectResult(flow = viewModel.numberFlow,
            successAction = { data ->
                navigateToDetailsScreen(data.data)
            })

        lifecycleScope.launch {
            viewModel.numbersHistoryFlow.collectLatest {
                adapter.setItems(it)
            }
        }
    }

    private fun navigateToDetailsScreen(data: NumberFactResponse) {
        if (getCurrentDestination() == R.id.FirstFragment) {
            navigate(
                MainFragmentDirections.actionFirstFragmentToSecondFragment(
                    data.number.roundToLong().toString(),
                    data.text
                )
            )
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