package com.example.myapplication.ui.user_screen.users_photo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.example.myapplication.constants.Const
import com.example.myapplication.databinding.FragmentUserPhotoBinding

import com.example.myapplication.domain.model.UserPhoto
import javax.inject.Inject

class UserPhotoFragment : Fragment(), ClickListener {

    @Inject
    lateinit var viewModel: UserPhotoViewModel

    private val adapter = UsersPhotoAdapter(this)

    private val userName: String
        get() = arguments?.getString(Const.USER_NAME_KEY) ?: error("error")

    private var _binding: FragmentUserPhotoBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserPhotoBinding.inflate(inflater, container, false)
        viewModel.setArgs(userName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fppRvListPhoto.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())

        binding.fppRvListPhoto.layoutManager = layoutManager

        binding.fppRvListPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                Log.e("werwerw", "$lastPosition x ${adapter.itemCount}")
                if (lastPosition > (adapter.itemCount - 5)) {
                    Log.e("werwerw", "load")
                    viewModel.onLoadUserPhotos()
                    (binding.fppRvListPhoto.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                        false
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        observeData(viewModel.userPhotoList) { event ->
            when (event) {
                is Event.Loading -> onProgress()
                is Event.Success -> onSuccess(event.data)
                is Event.Error -> onError()
            }
        }
        binding.fppSrlRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.onRefreshUserPhoto()
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
        binding.fppSrlRefresh.isRefreshing = false
    }

    private fun onSuccess(data: List<UserPhoto>) {
        try {
            binding.fppRvListPhoto.visibility = View.VISIBLE
            adapter.addUsersPhoto(data)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    private fun onError() {
        snackbar(getString(R.string.network_is_disconnected_text))
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
        findNavController().navigate(R.id.action_profileFragment_to_photoDetailsFragment, bundle)
    }


//    override fun onDestroyView() {
//        _binding = null
//        super.onDestroyView()
//    }

}