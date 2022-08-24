package com.priesniakov.interestingfactsaboutnumbers.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.priesniakov.interestingfactsaboutnumbers.core.data.*
import com.priesniakov.interestingfactsaboutnumbers.screens.common.ProgressDialog
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<T : ViewBinding>(
    private val createViewBinding: Inflate<T>
) : Fragment() {
    private var _binding: T? = null
    protected val binding: T get() = _binding!!
    private val navController: NavController by lazy { findNavController() }

    protected var showModalProgress: Boolean = false
        set(value) {
            if (value == field) return else field = value
            if (value) {
                showModalProgress()
            } else {
                hideModalProgress()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = createViewBinding.invoke(inflater, container, false)
        return binding.root
    }

    private fun showModalProgress() {
        hideModalProgress()
        ProgressDialog.newInstance()
            .showNow(childFragmentManager, ProgressDialog.PROGRESS_DIALOG_TAG)
    }

    private fun hideModalProgress() {
        dismissDialogByTag(ProgressDialog.PROGRESS_DIALOG_TAG)
    }

    private fun Fragment.dismissDialogByTag(tag: String) {
        (childFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismissAllowingStateLoss()
    }

    protected fun navigate(directions: NavDirections) {
        val currentDestination =
            (navController.currentDestination as? FragmentNavigator.Destination)?.className
        if (currentDestination == this.javaClass.name) {
            navController.navigate(directions)
        }
    }

    protected fun getCurrentDestination() = navController.currentDestination?.id

    protected fun <D : Any, R : Resource<D>> collectResult(
        flow: StateFlow<R>,
        successAction: (ResourceSuccess<D>) -> Unit,
        errorAction: (ResourceError<D>) -> Unit = { showError(it.message) }
    ) {
        lifecycleScope.launch {
            flow.collectLatest {
                when (it) {
                    ResourceIdle -> showModalProgress = false
                    ResourceLoading -> showModalProgress = true
                    is ResourceError<*> -> {
                        showModalProgress = false
                        errorAction(it as ResourceError<D>)
                    }
                    is ResourceSuccess<*> -> {
                        showModalProgress = false
                        successAction(it as ResourceSuccess<D>)
                    }
                }
            }
        }
    }

    protected fun showError(message: String) {
        val snackBar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        snackBar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}