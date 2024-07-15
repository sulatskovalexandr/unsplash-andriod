package com.unsplash.sulatskov.ui.collection_screens.collection_details

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
import com.unsplash.sulatskov.databinding.FragmentCollectionDetailsBinding
import com.unsplash.sulatskov.domain.model.CollectionPhotos
import com.unsplash.sulatskov.ui.base.BaseFragment

class CollectionDetailsFragment :
    BaseFragment<CollectionDetailsViewModel, FragmentCollectionDetailsBinding>(),
    CollectionPhotoClickListener {

    private val adapter = CollectionPhotosAdapter(this)

    /**
     * Подписка на получение и обновление данных из CollectionDetailsViewModel
     */
    override val viewModelClass: Class<CollectionDetailsViewModel>
        get() = CollectionDetailsViewModel::class.java

    override fun createViewBinding(): FragmentCollectionDetailsBinding =
        FragmentCollectionDetailsBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    private val title: String
        get() = arguments?.getString(Const.TITLE) ?: error("error")

    private val collectionId: String
        get() = arguments?.getString(Const.COLLECTION_ID) ?: error("error")

    override fun observeViewModel() {
        /**
         * Получение и обновление информации о коллекций из CollectionViewModel
         */
        observeData(viewModel.collectionDetails) { collectionDetails ->
            if (collectionDetails.description != null) {
                binding.fcdDescription.visibility = View.VISIBLE
                binding.fcdDescription.text = collectionDetails.description
            }
            binding.fcdToolbar.title = title
            binding.fcdTotalPhotoAndUser.text =
                "${collectionDetails.totalPhotos} Фотографии  •  Автор ${collectionDetails.user.userName} "

            binding.fcdTotalPhotoAndUser.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(
                    Const.PHOTO_PROFILE_KEY,
                    collectionDetails.user.profileImage?.medium
                )
                bundle.putString(Const.USER_NAME_KEY, collectionDetails.user.userName)
                findNavController().navigate(
                    R.id.action_collectionDetailsFragment_to_userFragment,
                    bundle
                )
            }
        }

        /**
         * Получение и обновление данных о списке фотограций коллекции из CollectionViewModel
         */

        observeData(viewModel.collectionPhotos) { event ->
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
                is Messages.NetworkIsDisconnected -> {
                    binding.fcdDisconnected.visibility = View.VISIBLE
                }
                is Messages.ShowShimmer -> {
                    binding.fcdShimmerFrameLayout.startShimmer()
                    binding.fcdShimmerFrameLayout.visibility = View.VISIBLE
                }
                is Messages.HideShimmer -> {
                    binding.fcdShimmerFrameLayout.stopShimmer()
                    binding.fcdShimmerFrameLayout.visibility = View.GONE
                }
                else -> {
                }
            }
            viewModel.clearMessage()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setCollectionId(collectionId = collectionId)
        super.onViewCreated(view, savedInstanceState)

        binding.fcdToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.fcdRvListCollectionPhotos.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.fcdRvListCollectionPhotos.layoutManager = layoutManager

        /**
         * Прогрузка следующей страницы в списке фото по достижении 5-го элемента страницы
         */
        binding.fcdRvListCollectionPhotos.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition > (adapter.itemCount - 5)) {
                    viewModel.onLoadCollectionPhotos()
                    (binding.fcdRvListCollectionPhotos.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                        false
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    /**
     * Действия в момент загрузки данных
     */
    private fun onProgress() {
    }

    /**
     * Действия в момент успешной загрузки данных
     */
    private fun onSuccess(data: List<CollectionPhotos>) {
        try {
            if (data.isNotEmpty()) {
                binding.fcdRvListCollectionPhotos.visibility = View.VISIBLE
                adapter.addCollectionPhoto(data)
            } else {
                binding.fcdEmptyList.visibility = View.VISIBLE
            }
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
            R.id.action_collectionDetailsFragment_to_photoDetailsFragment,
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
        bundle.putString(Const.PHOTO_PROFILE_KEY, photoProfile)
        bundle.putString(Const.USER_NAME_KEY, userName)
        findNavController().navigate(
            R.id.action_collectionDetailsFragment_to_userFragment,
            bundle,
            fragmentAnim()
        )
    }
}