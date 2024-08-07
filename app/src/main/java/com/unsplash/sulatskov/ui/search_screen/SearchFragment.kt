package com.unsplash.sulatskov.ui.search_screen

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.appComponent
import com.unsplash.sulatskov.common.textFlow
import com.unsplash.sulatskov.databinding.FragmentSearchBinding
import com.unsplash.sulatskov.ui.base.BaseFragment
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    override val viewModelClass: Class<SearchViewModel>
        get() = SearchViewModel::class.java

    override fun createViewBinding(): FragmentSearchBinding =
        FragmentSearchBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    private val title: String?
        get() = arguments?.getString(TAG_TITLE_KEY)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fsToolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val searchPagerAdapter = SearchPagerAdapter(fm = childFragmentManager, requireContext())
        binding.fsEditText.textFlow
            .debounce(500L)
            .onEach { query ->
                binding.fsDeleteButton.isVisible = query.isNotEmpty()
                searchPagerAdapter.onQuery(query)
            }.launchIn(lifecycleScope)


        binding.fsDeleteButton.setOnClickListener {
            binding.fsEditText.text = null
        }


        binding.fsViewPager.adapter = searchPagerAdapter
        binding.fsTabLayout.getTabAt(0)?.text = getString(R.string.pf_text_photo)
        binding.fsTabLayout.getTabAt(1)?.text = getString(R.string.pf_text_collection)
//        binding.fsTabLayout.getTabAt(2)?.text = getString(R.string.fs_text_users)
        if (title != null) {
            binding.fsEditText.setText(title)
        }
    }

    companion object {
        const val TAG_TITLE_KEY = "TAG_TITLE_KEY"
        fun createArgs(tagTitle: String) = Bundle().apply {
            putString(TAG_TITLE_KEY, tagTitle)
        }
    }
}