package com.example.myapplication.ui.user_screen.user_collection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import javax.inject.Inject


class UserCollectionFragment : Fragment(), ClickListener {

    @Inject
    lateinit var viewModel: UserCollectionViewModel

    private val adapter = UserCollectionAdapter(this)

    private val userName: String
        get() = arguments?.getString(Const.USER_NAME_KEY) ?: error("error")

    private var _binding: FragmentUserCollectionBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserCollectionBinding.inflate(inflater, container, false)
        viewModel.setArgs(userName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fpcRvListCollection.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())

        binding.fpcRvListCollection.layoutManager = layoutManager

        binding.fpcRvListCollection.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                Log.e("werwerw", "$lastPosition x ${adapter.itemCount}")
                if (adapter.itemCount > 20) {
                    if (lastPosition > (adapter.itemCount - 5)) {
                        Log.e("werwerw", "load")
                        viewModel.onLoadUserCollection()
                        (binding.fpcRvListCollection.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                            false
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            }
        })


        binding.fpcSrlRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.onRefreshUserCollection()
        }
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
                else -> {
                }
            }

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


    override fun onDestroy() {
        binding.fppShimmerFrameLayout.stopShimmer()
        super.onDestroy()
    }

    override fun onCollectionClick(
        photoId: String,
        photoUrl: String,
        photoProfile: String,
        userName: String
    ) {
        TODO("Not yet implemented")
    }
}