package com.unsplash.sulatskov.ui.user_screen.users_photo

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.unsplash.sulatskov.Event
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.appComponent
import com.unsplash.sulatskov.common.Messages
import com.unsplash.sulatskov.common.fragmentAnim
import com.unsplash.sulatskov.common.observeData
import com.unsplash.sulatskov.common.snackbar
import com.unsplash.sulatskov.constants.Const
import com.unsplash.sulatskov.databinding.FragmentUserPhotoBinding
import com.unsplash.sulatskov.domain.model.UserPhoto
import com.unsplash.sulatskov.ui.base.BaseFragment

class
UserPhotoFragment : BaseFragment<UserPhotoViewModel, FragmentUserPhotoBinding>(),
    ClickListener {

    /**
     * Подписка на получение и обновление данных из UserPhotoViewModel
     */
    override val viewModelClass: Class<UserPhotoViewModel>
        get() = UserPhotoViewModel::class.java

    private val adapter = UsersPhotoAdapter(this)

    private val userName: String
        get() = arguments?.getString(Const.USER_NAME_KEY) ?: error("error")

    override fun createViewBinding(): FragmentUserPhotoBinding =
        FragmentUserPhotoBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    override fun observeViewModel() {

        /**
         * Получение и обновление данных о списке фото из UserPhotoViewModel
         */
        observeData(viewModel.userPhotoList) { event ->
            when (event) {
                is Event.Loading -> onProgress()
                is Event.Success -> onSuccess(event.data)
                is Event.Error -> onError()
            }
        }

        /**
         * Получение и обновление данных о Messages из UserPhotoViewModel
         */
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
                    binding.fupDisconnected.visibility = View.VISIBLE
                }
                else -> {
                }
            }
        }
    }

    /**
     * Переход на PhotoDetailsFragment с передачей аргументов
     *
     * @param photoId
     * @param photoUrl
     * @param photoProfile
     * @param userName
     */
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
            R.id.action_profileFragment_to_photoDetailsFragment,
            bundle,
            fragmentAnim()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setArgs(userName)
        super.onViewCreated(view, savedInstanceState)

        binding.fppRvListPhoto.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())

        binding.fppRvListPhoto.layoutManager = layoutManager

        /**
         * Прогрузка следующей страницы в списке фото пользователя по достижении 5-го элемента страницы
         */
        binding.fppRvListPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition > (adapter.itemCount - 5)) {
                    viewModel.onLoadUserPhotos()
                    (binding.fppRvListPhoto.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                        false

                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.fppSrlRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.onRefreshUserPhoto()
            binding.fupDisconnected.visibility = View.GONE
            binding.fupEmptyList.visibility = View.GONE
        }
    }

    /**
     * Действия в момент загрузки данных
     */
    private fun onProgress() {
        binding.fppSrlRefresh.isRefreshing = false
    }

    /**
     * Действия в момент успешной загрузки данных
     */
    private fun onSuccess(data: List<UserPhoto>) {
        try {
            binding.fppRvListPhoto.visibility = View.VISIBLE
            adapter.addUsersPhoto(data)

            if (adapter.itemCount == 0)
                binding.fupEmptyList.visibility = View.VISIBLE

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
}