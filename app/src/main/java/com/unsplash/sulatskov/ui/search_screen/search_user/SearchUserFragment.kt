package com.unsplash.sulatskov.ui.search_screen.search_user

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
import com.unsplash.sulatskov.constants.Const.PHOTO_ID_KEY
import com.unsplash.sulatskov.constants.Const.PHOTO_PROFILE_KEY
import com.unsplash.sulatskov.constants.Const.PHOTO_URL_KEY
import com.unsplash.sulatskov.constants.Const.USER_NAME_KEY
import com.unsplash.sulatskov.databinding.FragmentSearchUserBinding
import com.unsplash.sulatskov.ui.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchUserFragment : BaseFragment<SearchUserViewModel, FragmentSearchUserBinding>(),
    SearchUserClickListener {

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PagingSearchUserAdapter(this)
    }
    override val viewModelClass: Class<SearchUserViewModel>
        get() = SearchUserViewModel::class.java

    override fun createViewBinding(): FragmentSearchUserBinding =
        FragmentSearchUserBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fsuRvListUser.adapter = adapter.withLoadStateHeaderAndFooter(
            header = SearchUserLoadingStateAdapter(adapter),
            footer = SearchUserLoadingStateAdapter(adapter)
        )

        lifecycleScope.launch {
            viewModel.searchUsersList.collectLatest { pagingData ->
                adapter.submitData(pagingData = pagingData)
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.fsuRvListUser.layoutManager = layoutManager

        adapter.addLoadStateListener { state: CombinedLoadStates ->
            binding.fsuRvListUser.isVisible = state.refresh != LoadState.Loading
            binding.fsuShimmerFrameLayout.isVisible = state.refresh == LoadState.Loading
        }
    }

    fun onQuery(query: String) {
        adapter.refresh()
        viewModel.setArgs(query)
    }

    override fun onCardClick(photoProfile: String, userName: String) {
        val bundle = Bundle()
        bundle.putString(PHOTO_PROFILE_KEY, photoProfile)
        bundle.putString(USER_NAME_KEY, userName)
        findNavController().navigate(R.id.action_searchFragment_to_userFragment, bundle)

    }

    override fun onPhotoClick(
        photoId: String,
        photoUrl: String,
        photoProfile: String,
        userName: String
    ) {
        val bundle = Bundle()
        bundle.putString(PHOTO_ID_KEY, photoId)
        bundle.putString(PHOTO_URL_KEY, photoId)
        bundle.putString(PHOTO_PROFILE_KEY, photoId)
    }


}