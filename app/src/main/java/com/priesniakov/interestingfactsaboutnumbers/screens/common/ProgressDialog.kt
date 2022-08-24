package com.priesniakov.interestingfactsaboutnumbers.screens.common

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.priesniakov.interestingfactsaboutnumbers.R

internal class ProgressDialog : DialogFragment() {

    companion object {
        const val PROGRESS_DIALOG_TAG = "progress_dialog"
        fun newInstance(): ProgressDialog {
            return ProgressDialog().apply {
                isCancelable = false
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also {
            setStyle(STYLE_NO_TITLE, R.style.NumbersFacts_ProgressDialog)
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.window?.setDimAmount(0f)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_progress, container, false)
    }
}
