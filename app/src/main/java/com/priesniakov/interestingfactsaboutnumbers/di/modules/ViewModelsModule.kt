package com.priesniakov.interestingfactsaboutnumbers.di.modules

import com.priesniakov.interestingfactsaboutnumbers.screens.main.MainViewModel
import com.priesniakov.interestingfactsaboutnumbers.screens.main.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel<MainViewModel> { MainViewModelImpl(get()) }
}