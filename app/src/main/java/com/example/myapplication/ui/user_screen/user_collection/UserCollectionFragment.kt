package com.example.myapplication.ui.user_screen.user_collection

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.myapplication.Event
import com.example.myapplication.R
import com.example.myapplication.appComponent
import com.example.myapplication.common.Messages
import com.example.myapplication.common.observeData
import com.example.myapplication.common.snackbar
import com.example.myapplication.constants.Const
import com.example.myapplication.databinding.FragmentUserCollectionBinding
import com.example.myapplication.domain.model.Collection
import com.example.myapplication.ui.base.BaseFragment


class UserCollectionFragment :
    BaseFragment<UserCollectionViewModel, FragmentUserCollectionBinding>(), ClickListener {

    override val viewModelClass: Class<UserCollectionViewModel>
        get() = UserCollectionViewModel::class.java

    private val adapter = UserCollectionAdapter(this)

    private val userName: String
        get() = arguments?.getString(Const.USER_NAME_KEY) ?: error("error")

    override fun createViewBinding(): FragmentUserCollectionBinding =
        FragmentUserCollectionBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    override fun observeViewModel() {
        observeData(viewModel.userCollectionList) { event ->
            when (event) {
                is Event.Loading -> onProgress()
                is Event.Success -> onSuccess(event.data)
                is Event.Error -> onError()
            }
        }

        observeData(viewModel.messageFlow) { messages ->
            when (messages) {
                is Messages.HideShimmer -> {
                    binding.fppShimmerFrameLayout.visibility = View.GONE
                    binding.fppShimmerFrameLayout.stopShimmer()
                }
                is Messages.ShowShimmer -> {
                    binding.fppShimmerFrameLayout.visibility = View.VISIBLE
                    binding.fppShimmerFrameLayout.startShimmer()
                }
                is Messages.NetworkIsDisconnected -> {
                    binding.fucDisconnected.visibility = View.VISIBLE
                }
                else -> {
                }
            }

        }
    }

    override fun onCollectionClick(
        photoId: String,
        photoUrl: String,
        photoProfile: String,
        userName: String
    ) {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setArgs(userName)
        super.onViewCreated(view, savedInstanceState)
        binding.fpcRvListCollection.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())

        binding.fpcRvListCollection.layoutManager = layoutManager

        binding.fpcRvListCollection.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition > (adapter.itemCount - 5)) {
                    viewModel.onLoadUserCollection()
                    (binding.fpcRvListCollection.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                        false
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.fpcSrlRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.onRefreshUserCollection()
            binding.fucDisconnected.visibility = View.GONE
        }
    }

    private fun onProgress() {
        binding.fpcSrlRefresh.isRefreshing = false
        binding.fucEmptyList.visibility = View.GONE
    }

    private fun onSuccess(data: List<Collection>) {
        try {
            binding.fpcRvListCollection.visibility = View.VISIBLE
            adapter.addUsersPhoto(data)

            if (adapter.itemCount == 0)
                binding.fucEmptyList.visibility = View.VISIBLE

        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    private fun onError() {
        snackbar(getString(R.string.network_is_disconnected_text))

    }
}