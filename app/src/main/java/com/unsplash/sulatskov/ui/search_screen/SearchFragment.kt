package com.unsplash.sulatskov.ui.search_screen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.appComponent
import com.unsplash.sulatskov.common.showKeyboard
import com.unsplash.sulatskov.databinding.FragmentSearchBinding
import com.unsplash.sulatskov.ui.base.BaseFragment


class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    override val viewModelClass: Class<SearchViewModel>
        get() = SearchViewModel::class.java

    override fun createViewBinding(): FragmentSearchBinding =
        FragmentSearchBinding.inflate(layoutInflater)

    override fun inject() {
      appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fsEditText.requestFocus()
        binding.fsEditText.showKeyboard()

        binding.fsToolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.fsEditText.addTextChangedListener {
            if (it != null) {
                binding.fsDeleteButton.isVisible = it.toString().isNotEmpty()
            }
        }
        binding.fsDeleteButton.setOnClickListener {
            binding.fsEditText.text = null
        }

        binding.fsViewPager.adapter = SearchPagerAdapter(fm = childFragmentManager)
        binding.fsTabLayout.getTabAt(0)?.text = getString(R.string.pf_text_photo)
        binding.fsTabLayout.getTabAt(1)?.text = getString(R.string.pf_text_collection)
        binding.fsTabLayout.getTabAt(2)?.text = getString(R.string.fs_text_users)
    }
}