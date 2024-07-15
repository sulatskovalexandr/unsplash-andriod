package com.unsplash.sulatskov.ui.collection_screens

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
import com.unsplash.sulatskov.databinding.FragmentCollectionBinding
import com.unsplash.sulatskov.ui.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CollectionFragment : BaseFragment<CollectionViewModel, FragmentCollectionBinding>(),
    CollectionClickListener {

    //    private val adapter = CollectionAdapter(this)
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PagingCollectionAdapter(this)
    }

    /**
     * Подписка на получение и обновление данных из CollectionViewModel
     */
    override val viewModelClass: Class<CollectionViewModel>
        get() = CollectionViewModel::class.java

    override fun createViewBinding(): FragmentCollectionBinding =
        FragmentCollectionBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    /**
     * @see BaseFragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fcRvListCollection.adapter = adapter.withLoadStateHeaderAndFooter(
            header = CollectionLoadingStateAdapter(adapter),
            footer = CollectionLoadingStateAdapter(adapter)
        )
        val layoutManager = LinearLayoutManager(requireContext())
        binding.fcRvListCollection.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.listCollection.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        adapter.addLoadStateListener { state: CombinedLoadStates ->
            binding.fcRvListCollection.isVisible = state.refresh != LoadState.Loading
            binding.fcShimmerFrameLayout.isVisible = state.refresh == LoadState.Loading
        }

        /**
         * Обновление списка коллекций
         */
        binding.fcSrlRefresh.setOnRefreshListener {
            adapter.refresh()
            binding.fcSrlRefresh.isRefreshing = false
        }

        /**
         * Обработка нажатий на элементы в меню в Toolbar
         */
        binding.fcToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.item_order) {
            } else if (it.itemId == R.id.item_search) {
                findNavController().navigate(R.id.action_collectionFragment_to_searchFragment)
            }
            return@setOnMenuItemClickListener false
        }
    }

    /**
     * Переход на UserFragment с передачей аргументов
     *
     * @param photoProfile
     * @param userName
     */
    override fun onProfileImageClick(photoProfile: String, userName: String) {
        val bundle = Bundle()
        bundle.putString(Const.PHOTO_PROFILE_KEY, photoProfile)
        bundle.putString(Const.USER_NAME_KEY, userName)
        findNavController().navigate(
            R.id.action_collectionFragment_to_userFragment, bundle,
            fragmentAnim()
        )
    }

    /**
     * Переход на СollectionFragment с передачей аргументов
     *
     * @param collectionId
     * @param title
     */

    override fun onCollectionClick(collectionId: String, title: String) {
        val bundle = Bundle()
        bundle.putString(Const.COLLECTION_ID_KEY, collectionId)
        bundle.putString(Const.TITLE_KEY, title)
        findNavController().navigate(
            R.id.action_collectionFragment_to_collectionDetailsFragment, bundle,
            fragmentAnim()
        )
    }
}