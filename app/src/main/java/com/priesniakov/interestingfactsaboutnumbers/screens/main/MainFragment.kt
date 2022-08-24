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
import com.priesniakov.interestingfactsaboutnumbers.databinding.FragmentFirstBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainFragment : BaseFragment<FragmentFirstBinding>(FragmentFirstBinding::inflate) {

    private val viewModel: MainViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupListeners()
        subscribeUI()
    }

    private fun subscribeUI() {
        lifecycleScope.launch {
            viewModel.numberFlow.collectLatest {
                showModalProgress = false
                when (it) {
                    is ResourceError -> Log.d("NumTag", it.message)
                    ResourceLoading -> showModalProgress = true
                    is ResourceSuccess -> Log.d("NumTag", it.data.text)
                    ResourceIdle -> {
                    }
                }
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
    }
}