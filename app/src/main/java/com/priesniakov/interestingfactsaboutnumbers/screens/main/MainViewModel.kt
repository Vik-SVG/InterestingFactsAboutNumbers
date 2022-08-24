package com.priesniakov.interestingfactsaboutnumbers.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priesniakov.interestingfactsaboutnumbers.core.data.ResourceIdle
import com.priesniakov.interestingfactsaboutnumbers.data.common.NumberFact
import com.priesniakov.interestingfactsaboutnumbers.data.entity.NumberFactResponse
import com.priesniakov.interestingfactsaboutnumbers.data.usecases.NumberFactsUseCase
import com.priesniakov.interestingfactsaboutnumbers.data.usecases.NumbersHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class MainViewModel : ViewModel() {
    abstract fun getRandomFact()
    abstract fun getNumberFact(number: String)
    abstract val numberFlow: StateFlow<NumberFact>
    abstract fun getHistory()
    abstract fun clearData()

    abstract val numbersHistoryFlow: StateFlow<List<NumberFactResponse>>
}

class MainViewModelImpl(
    private val numbersUseCase: NumberFactsUseCase,
    private val historyUseCase: NumbersHistoryUseCase
) : MainViewModel() {

    private val _numberFlow = MutableStateFlow<NumberFact>(ResourceIdle)
    override val numberFlow: StateFlow<NumberFact> = _numberFlow

    private val _numbersHistoryFlow = MutableStateFlow<List<NumberFactResponse>>(listOf())
    override val numbersHistoryFlow: StateFlow<List<NumberFactResponse>> = _numbersHistoryFlow

    override fun getRandomFact() {
        viewModelScope.launch(Dispatchers.IO) {
            numbersUseCase().collectLatest { _numberFlow.emit(it) }
        }
    }

    override fun getHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            historyUseCase().collectLatest { _numbersHistoryFlow.emit(it) }
        }
    }

    override fun clearData() {
        _numberFlow.value = ResourceIdle
    }

    override fun getNumberFact(number: String) {
        number.toIntOrNull()?.let { intNumber ->
            viewModelScope.launch(Dispatchers.IO) {
                numbersUseCase(intNumber).collectLatest { _numberFlow.emit(it) }
            }
        }
    }
}