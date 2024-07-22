package com.unsplash.sulatskov.ui.search_screen.search_collection

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.appComponent
import com.unsplash.sulatskov.common.fragmentAnim
import com.unsplash.sulatskov.constants.Const
import com.unsplash.sulatskov.databinding.FragmentSearchCollectionBinding
import com.unsplash.sulatskov.ui.base.BaseFragment
import com.unsplash.sulatskov.ui.collection_screens.CollectionClickListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchCollectionFragment :
    BaseFragment<SearchCollectionViewModel, FragmentSearchCollectionBinding>(),
    CollectionClickListener {

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PagingSearchCollectionAdapter(this)
    }
    override val viewModelClass: Class<SearchCollectionViewModel>
        get() = SearchCollectionViewModel::class.java

    override fun createViewBinding(): FragmentSearchCollectionBinding =
        FragmentSearchCollectionBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fpcRvListCollection.adapter = adapter.withLoadStateHeaderAndFooter(
            header = SearchCollectionLoadingStateAdapter(adapter),
            footer = SearchCollectionLoadingStateAdapter(adapter)
        )

        lifecycleScope.launch {
            viewModel.searchCollectionsList.collectLatest { pagingData ->
                adapter.submitData(pagingData = pagingData)
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.fpcRvListCollection.layoutManager = layoutManager

        adapter.addLoadStateListener { state: CombinedLoadStates ->
            binding.fpcRvListCollection.isVisible = state.refresh != LoadState.Loading
            binding.fscShimmerFrameLayout.isVisible = state.refresh == LoadState.Loading
        }
    }

    fun onQuery(query: String) {
        adapter.refresh()
        viewModel.setArgs(query)
    }

    override fun onCollectionClick(collectionId: String, title: String) {
        val bundle = Bundle()
        bundle.putString(Const.COLLECTION_ID_KEY, collectionId)
        bundle.putString(Const.TITLE_KEY, title)
        findNavController().navigate(
            R.id.action_searchFragment_to_collectionDetailsFragment, bundle,
            fragmentAnim()
        )
    }

    override fun onProfileImageClick(photoProfile: String, userName: String) {
        val bundle = Bundle()
        bundle.putString(Const.PHOTO_PROFILE_KEY, photoProfile)
        bundle.putString(Const.USER_NAME_KEY, userName)
        findNavController().navigate(
            R.id.action_searchFragment_to_userFragment, bundle,
            fragmentAnim()
        )
    }
}