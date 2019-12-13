package mounts.com.driver.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import mounts.com.driver.R
import mounts.com.driver.databinding.BottomSheetFragmentBinding

class BottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = BottomSheetFragment()
    }

    private lateinit var viewModel: BottomSheetViewModel
    private lateinit var  binding: BottomSheetFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(BottomSheetViewModel::class.java)

        binding.viewModel = viewModel
        return binding.root
    }


}
