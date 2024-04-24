package com.unsplash.sulatskov.ui.search_screen.search_photo

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
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
import com.unsplash.sulatskov.databinding.FragmentSearchPhotoBinding
import com.unsplash.sulatskov.ui.base.BaseFragment
import com.unsplash.sulatskov.ui.collection_screens.CollectionLoadingStateAdapter
import com.unsplash.sulatskov.ui.photo_screen.PhotoClickListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchPhotoFragment : BaseFragment<SearchPhotoViewModel, FragmentSearchPhotoBinding>(),
    PhotoClickListener {

//    private val adapter = SearchPhotoAdapter(this)
//    }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PagingSearchPhotoAdapter(this)
    }
    override val viewModelClass: Class<SearchPhotoViewModel>
        get() = SearchPhotoViewModel::class.java

    override fun createViewBinding(): FragmentSearchPhotoBinding =
        FragmentSearchPhotoBinding.inflate(layoutInflater)

    override fun inject() = appComponent.inject(this)

//    override fun observeViewModel() {
//        observeData(viewModel.searchPhotosList) { event ->
//            when (event) {
//                is Event.Loading -> onProgress()
//                is Event.Success -> onSuccess(event.data)
//                is Event.Error -> onError()
//            }
//        }
//        observeData(viewModel.messageFlow) { message ->
//            when (message) {
//                is Messages.NetworkIsDisconnected ->
//                    snackbar(getString(R.string.network_is_disconnected_text))
//
//                is Messages.ShowShimmer -> {
//                    binding.fspShimmerFrameLayout.startShimmer()
//                    binding.fspShimmerFrameLayout.visibility = View.VISIBLE
//                }
//
//                is Messages.HideShimmer -> {
//                    binding.fspShimmerFrameLayout.stopShimmer()
//                    binding.fspShimmerFrameLayout.visibility = View.GONE
//                }
//
//                else -> {
//                }
//            }
//            viewModel.clearMessage()
//        }
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fspRvListPhoto.adapter = adapter.withLoadStateHeaderAndFooter(
            header = SearchPhotoLoadingStateAdapter(adapter = adapter),
            footer = SearchPhotoLoadingStateAdapter(adapter)
        )

        lifecycleScope.launch {
            viewModel.searchPhotosList.collectLatest { pagingData->
                adapter.submitData(pagingData)

            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.fspRvListPhoto.layoutManager = layoutManager

        adapter.addLoadStateListener { state: CombinedLoadStates ->
            if (adapter == null) binding.fspNotFound.visibility = View.VISIBLE
            binding.fspRvListPhoto.isVisible = state.refresh != LoadState.Loading
            binding.fspShimmerFrameLayout.isVisible = state.refresh == LoadState.Loading
        }

//        binding.fspRvListPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                val lastPosition = layoutManager.findLastVisibleItemPosition()
//                if (lastPosition > (adapter.itemCount - 5)) {
//                    viewModel.onLoadSearchPhotos()
//                    (binding.fspRvListPhoto.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
//                        false
//                }
//                super.onScrolled(recyclerView, dx, dy)
//            }
//        })
    }

//    private fun onProgress() {}
//
//    private fun onSuccess(data: List<PhotoDto>) {
//        try {
//            if (adapter.itemCount == 0) {
//                adapter.setPhoto(data)
//                binding.fspRvListPhoto.visibility = View.VISIBLE
//                binding.fspRvListPhoto.visibility = View.GONE
//            } else {
//                adapter.addPhoto(data)
//                binding.fspRvListPhoto.visibility = View.VISIBLE
//            }
//
//        } catch (t: Throwable) {
//            t.printStackTrace()
//        }
//    }

//    private fun onError() {
//
//        snackbar(getString(R.string.network_is_disconnected_text))
//    }

    fun onQuery(query: String) {
        adapter.refresh()
        viewModel.setArgs(query)
    }

    override fun onPhotoClick(
        photoId: String,
        photoUrl: String,
        photoProfile: String,
        userName: String
    ) {
        val bundle = Bundle()
        bundle.putString(Const.PHOTO_ID_KEY, photoId)
        bundle.putString(Const.PHOTO_URL_KEY, photoUrl)
        bundle.putString(Const.PHOTO_PROFILE_KEY, photoProfile)
        bundle.putString(Const.USER_NAME_KEY, userName)
        findNavController().navigate(
            R.id.action_searchFragment_to_photoDetailsFragment,
            bundle,
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


