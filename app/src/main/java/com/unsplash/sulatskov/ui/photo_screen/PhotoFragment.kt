package com.unsplash.sulatskov.ui.photo_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.unsplash.sulatskov.Event
import com.unsplash.sulatskov.MainActivity
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.appComponent
import com.unsplash.sulatskov.common.Messages
import com.unsplash.sulatskov.common.fragmentAnim
import com.unsplash.sulatskov.common.observeData
import com.unsplash.sulatskov.common.snackbar
import com.unsplash.sulatskov.constants.Const
import com.unsplash.sulatskov.constants.Const.PHOTO_PROFILE_KEY
import com.unsplash.sulatskov.constants.Const.PHOTO_URL_KEY
import com.unsplash.sulatskov.constants.Const.USER_NAME_KEY
import com.unsplash.sulatskov.databinding.FragmentPhotoBinding
import com.unsplash.sulatskov.domain.model.Photo
import com.unsplash.sulatskov.ui.base.BaseFragment


class PhotoFragment : BaseFragment<PhotoViewModel, FragmentPhotoBinding>(), PhotoClickListener {

    /**
     * Подписка на получение и обновление данных из PhotoViewModel
     */
    override val viewModelClass: Class<PhotoViewModel>
        get() = PhotoViewModel::class.java

    private val adapter = PhotoAdapter(this) // Передача адаптера
    var checkedItemId = 0

    override fun createViewBinding(): FragmentPhotoBinding =
        FragmentPhotoBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    override fun observeViewModel() {

        /**
         * Получение и обновление данных о списке фото из PhotoViewModel
         */
        observeData(viewModel.photoList) { event ->
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
                    binding.fpShimmerFrameLayout.startShimmer()
                    binding.fpShimmerFrameLayout.visibility = View.VISIBLE
                }
                is Messages.HideShimmer -> {
                    binding.fpShimmerFrameLayout.stopShimmer()
                    binding.fpShimmerFrameLayout.visibility = View.GONE
                }
                else -> {
                }
            }
            viewModel.clearMessage()
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
        bundle.putString(PHOTO_URL_KEY, photoUrl)
        bundle.putString(PHOTO_PROFILE_KEY, photoProfile)
        bundle.putString(USER_NAME_KEY, userName)
        findNavController().navigate(
            R.id.action_photoFragment_to_photoDetailsFragment,
            bundle,
            fragmentAnim()
        )
    }

    /**
     * Переход на UserFragment с передачей аргументов
     *
     * @param photoProfile
     * @param userName
     */
    override fun onProfileImageClick(photoProfile: String, userName: String) {
        val bundle = Bundle()
        bundle.putString(PHOTO_PROFILE_KEY, photoProfile)
        bundle.putString(USER_NAME_KEY, userName)
        findNavController().navigate(
            R.id.action_photoFragment_to_userFragment, bundle,
            fragmentAnim()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fpRvListPhotos.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.fpRvListPhotos.layoutManager = layoutManager

        /**
         * Прогрузка следующей страницы в списке фото по достижении 5-го элемента страницы
         */
        binding.fpRvListPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                Log.e("werwerw", "$lastPosition x ${adapter.itemCount}")
                if (lastPosition > (adapter.itemCount - 5)) {
                    Log.e("werwerw", "load")
                    viewModel.onLoadPhotos()
                    (binding.fpRvListPhotos.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                        false
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        /**
         * Обновление списка фото
         */
        binding.fpSrlRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.onRefreshPhotos()
        }

        /**
         * Показ диалога для сортировки списка
         */
        fun showDialog() {
            AlertDialog.Builder(activity as MainActivity, R.style.MultiChoiceAlertDialog)
                .setTitle(R.string.order_dialog_text)
                .setSingleChoiceItems(R.array.sort, checkedItemId) { d, checkedId ->
                    when (checkedId) {
                        0 -> {
                            checkedItemId = 0
                            adapter.clear()
                            viewModel.onLoadPhotos()
                            d.dismiss()
                        }
                        1 -> {
                            checkedItemId = 1
                            adapter.clear()
                            viewModel.loadListOldestPhoto()
                            d.dismiss()
                        }
                        2 -> {
                            checkedItemId = 2
                            adapter.clear()
                            viewModel.loadListPopularPhoto()
                            d.dismiss()
                        }
                    }
                }.create()
                .show()
        }

        /**
         * Обработка нажатий на элеемнты в меню в Toolbar
         */
        binding.fpToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.item_order) {
                showDialog()
            } else if (it.itemId == R.id.item_search) {
                findNavController().navigate(R.id.action_photoFragment_to_searchFragment)
            }
            return@setOnMenuItemClickListener false
        }
    }

    /**
     * Действия в момент загрузки данных
     */
    private fun onProgress() {
        binding.fpSrlRefresh.isRefreshing = false
    }

    /**
     * Действия в момент успешной загрузки данных
     */
    private fun onSuccess(data: List<Photo>) {
        try {
            binding.fpRvListPhotos.visibility = View.VISIBLE
            adapter.addPhoto(data)
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