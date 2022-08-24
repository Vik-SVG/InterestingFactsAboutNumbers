package com.priesniakov.interestingfactsaboutnumbers.screens.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.priesniakov.interestingfactsaboutnumbers.R
import com.priesniakov.interestingfactsaboutnumbers.core.base.BaseFragment
import com.priesniakov.interestingfactsaboutnumbers.databinding.FragmentSecondBinding


class InterestingFactResultFragment :
    BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate) {

    private val args by navArgs<InterestingFactResultFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = with(binding) {
        number.text = args.number
        fact.text = args.fact
    }
}