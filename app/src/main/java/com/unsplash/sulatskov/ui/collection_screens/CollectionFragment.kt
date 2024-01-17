package com.unsplash.sulatskov.ui.collection_screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.unsplash.sulatskov.Event
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.appComponent
import com.unsplash.sulatskov.common.Messages
import com.unsplash.sulatskov.common.observeData
import com.unsplash.sulatskov.common.snackbar
import com.unsplash.sulatskov.constants.Const
import com.unsplash.sulatskov.databinding.FragmentCollectionBinding
import com.unsplash.sulatskov.domain.model.Collection
import com.unsplash.sulatskov.ui.base.BaseFragment

class CollectionFragment : BaseFragment<CollectionViewModel, FragmentCollectionBinding>(),
    CollectionClickListener {

    private val adapter = CollectionAdapter(this)

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

    override fun observeViewModel() {

        /**
         * Получение и обновление данных о списке коллекций из CollectionViewModel
         */
        observeData(viewModel.collectionList) { event ->
            when (event) {
                is Event.Loading -> onProgress()
                is Event.Success -> onSuccess(event.data)
                is Event.Error -> onError()
            }
        }

        /**
         * Получение и обновление данных о Messages из CollectionViewModel
         */
        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected ->
                    snackbar(getString(R.string.network_is_disconnected_text))
                is Messages.ShowShimmer -> {
                    binding.fcShimmerFrameLayout.startShimmer()
                    binding.fcShimmerFrameLayout.visibility = View.VISIBLE
                }
                is Messages.HideShimmer -> {
                    binding.fcShimmerFrameLayout.stopShimmer()
                    binding.fcShimmerFrameLayout.visibility = View.GONE
                }
                else -> {
                }
            }
            viewModel.clearMessage()
        }
    }

    /**
     * @see BaseFragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fcRvListCollection.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.fcRvListCollection.layoutManager =
            layoutManager

        /**
         * Прогрузка следующей страницы в списке коллекций по достижении 5-го элемента страницы
         */

        binding.fcRvListCollection.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                Log.e("werwerw", "$lastPosition x ${adapter.itemCount}")
                if (lastPosition > (adapter.itemCount - 5)) {
                    Log.e("werwerw", "load")
                    viewModel.onLoadCollection()
                    (binding.fcRvListCollection.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                        false
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        /**
         * Обновление списка коллекций
         */
        binding.fcSrlRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.onRefreshCollection()
        }

        /**
         * Обработка нажатий на элеемнты в меню в Toolbar
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
     * Действия в момент загрузки данных
     */
    private fun onProgress() {
        binding.fcSrlRefresh.isRefreshing = false
    }

    /**
     * Действия в момент успешной загрузки данных
     */
    private fun onSuccess(data: List<Collection>) {
        try {
            binding.fcRvListCollection.visibility = View.VISIBLE
            adapter.addCollection(data)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    /**
     * Действия в момент ошибки загрузки данных
     */
    private fun onError() {
        snackbar(getString(R.string.network_is_disconnected_text))
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
        findNavController().navigate(R.id.action_collectionFragment_to_userFragment, bundle)
    }
}
