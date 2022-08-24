package com.priesniakov.interestingfactsaboutnumbers.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priesniakov.interestingfactsaboutnumbers.core.data.ResourceIdle
import com.priesniakov.interestingfactsaboutnumbers.data.common.NumberFact
import com.priesniakov.interestingfactsaboutnumbers.data.usecases.NumberFactsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class MainViewModel : ViewModel() {
    abstract fun getRandomFact()
    abstract fun getNumberFact(number: String)
    abstract val numberFlow: MutableStateFlow<NumberFact>
}

class MainViewModelImpl(
    private val numbersUseCase: NumberFactsUseCase
) : MainViewModel() {

    override val numberFlow = MutableStateFlow<NumberFact>(ResourceIdle)

    override fun getRandomFact() {
    }

    override fun getNumberFact(number: String) {
        number.toIntOrNull()?.let { intNumber ->
            viewModelScope.launch(Dispatchers.IO) {
                numbersUseCase(intNumber).collectLatest {
                    numberFlow.emit(it)
                }
            }
        }
    }
}