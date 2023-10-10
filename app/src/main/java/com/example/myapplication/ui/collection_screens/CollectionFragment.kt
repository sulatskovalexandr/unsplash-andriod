package com.example.myapplication.ui.collection_screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.myapplication.Event
import com.example.myapplication.R
import com.example.myapplication.appComponent
import com.example.myapplication.common.Messages
import com.example.myapplication.common.observeData
import com.example.myapplication.common.snackbar
import com.example.myapplication.databinding.FragmentCollectionBinding
import com.example.myapplication.domain.model.Collection
import com.example.myapplication.ui.base.BaseFragment

class CollectionFragment : BaseFragment<CollectionViewModel, FragmentCollectionBinding>() {

    private val adapter = CollectionAdapter()

    override val viewModelClass: Class<CollectionViewModel>
        get() = CollectionViewModel::class.java

    override fun createViewBinding(): FragmentCollectionBinding =
        FragmentCollectionBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    override fun observeViewModel() {
        observeData(viewModel.collectionList) { event ->
            when (event) {
                is Event.Loading -> onProgress()
                is Event.Success -> onSuccess(event.data)
                is Event.Error -> onError()
            }
        }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fcRvListCollection.adapter = adapter // Подключение PhotosAdapter() к fgRvListPhotos
        val layoutManager = LinearLayoutManager(requireContext())
        binding.fcRvListCollection.layoutManager =
            layoutManager

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

        binding.fcSrlRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.onRefreshCollection()
        }

        binding.fcToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.item_order) {
            } else if (it.itemId == R.id.item_search) {
                findNavController().navigate(R.id.action_collectionFragment_to_searchFragment)
            }
            return@setOnMenuItemClickListener false
        }
    }

    override fun onDestroy() {
        binding.fcShimmerFrameLayout.stopShimmer()
        super.onDestroy()
    }

    private fun onProgress() {
        binding.fcSrlRefresh.isRefreshing = false
    }

    private fun onSuccess(data: List<Collection>) {
        try {
            binding.fcRvListCollection.visibility = View.VISIBLE
            adapter.addCollection(data)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    private fun onError() {
        snackbar(getString(R.string.network_is_disconnected_text))
    }


}
