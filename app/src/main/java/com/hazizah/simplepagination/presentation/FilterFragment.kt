package com.hazizah.simplepagination.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hazizah.simplepagination.databinding.FragmentFilterBinding

class FilterFragment : BottomSheetDialogFragment() {
    private var _viewBinding: FragmentFilterBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentFilterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.edtMaxPrice.addTextChangedListener {
            it?.let {

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
