package com.unsplash.sulatskov.ui.photo_screen.photo_details_screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.setPadding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.stfalcon.imageviewer.StfalconImageViewer
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.appComponent
import com.unsplash.sulatskov.common.ManageStorageContract
import com.unsplash.sulatskov.common.Messages
import com.unsplash.sulatskov.common.formated
import com.unsplash.sulatskov.common.fragmentAnim
import com.unsplash.sulatskov.common.isPhotoExists
import com.unsplash.sulatskov.common.observeData
import com.unsplash.sulatskov.common.snackbar
import com.unsplash.sulatskov.constants.Const.PHOTO_ID_KEY
import com.unsplash.sulatskov.constants.Const.PHOTO_PROFILE_KEY
import com.unsplash.sulatskov.constants.Const.PHOTO_URL_KEY
import com.unsplash.sulatskov.constants.Const.USER_NAME_KEY
import com.unsplash.sulatskov.databinding.FragmentPhotoDetailsBinding
import com.unsplash.sulatskov.photo_details_screen.presentation.photo_details_screen.PhotoDetailsAdapter
import com.unsplash.sulatskov.ui.base.BaseFragment
import com.unsplash.sulatskov.ui.search_screen.SearchFragment


class PhotoDetailsFragment : BaseFragment<PhotoDetailsViewModel, FragmentPhotoDetailsBinding>() {

    /**
     * Подписка на получение и обновление данных из PhotoViewModel
     */

    override val viewModelClass: Class<PhotoDetailsViewModel>
        get() = PhotoDetailsViewModel::class.java

    private val adapter = PhotoDetailsAdapter(
        onTagClick = { tagTitle ->
            findNavController().navigate(
                resId = R.id.action_photoDetailsFragment_to_searchFragment,
                args = SearchFragment.createArgs(tagTitle)
            )
        }
    )

    override fun createViewBinding(): FragmentPhotoDetailsBinding =
        FragmentPhotoDetailsBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    private val photoUrl: String
        get() = arguments?.getString(PHOTO_URL_KEY) ?: error("error")
    private val photoId: String
        get() = arguments?.getString(PHOTO_ID_KEY) ?: error("error")
    private val photoProfile: String
        get() = arguments?.getString(PHOTO_PROFILE_KEY) ?: error("error")
    private val userName: String
        get() = arguments?.getString(USER_NAME_KEY) ?: error("error")

    override fun observeViewModel() {

        /**
         * Получение и обновление данных фото из PhotoDetailsViewModel
         */

        observeData(viewModel.photoDetails) { photoDetails ->

            if (photoDetails.exif?.model != null) {
                binding.fpdTvCameraInfo.text = photoDetails.exif.model
            } else {
                binding.fpdTvCameraInfo.text = getString(R.string.unknown_text)
            }
            if (photoDetails.exif?.focalLength != null) {
                binding.fpdTvFocusInfo.text = "${photoDetails.exif.focalLength}mm"
            } else {
                binding.fpdTvFocusInfo.text = getString(R.string.unknown_text)
            }
            if (photoDetails.exif?.iso != null) {
                binding.fpdTvIsoInfo.text = photoDetails.exif.iso.toString()
            } else {
                binding.fpdTvIsoInfo.text = getString(R.string.unknown_text)
            }
            if (photoDetails.exif?.aperture != null) {
                binding.fpdTvApertureInfo.text = "f/${photoDetails.exif?.aperture}"
            } else {
                binding.fpdTvApertureInfo.text = getString(R.string.unknown_text)
            }
            if (photoDetails.exif?.exposition != null) {
                binding.fpdTvExpositionInfo.text = "${photoDetails.exif?.exposition}s"
            } else {
                binding.fpdTvExpositionInfo.text = getString(R.string.unknown_text)
            }
            binding.fpdTvResolutionInfo.text = "${photoDetails.width} x ${photoDetails.height}"

            binding.fpdTvNumberOfLikesInfo.text = photoDetails.likes?.formated

            if (photoDetails.location?.country != null && photoDetails.location?.city != null) {
                binding.fpdLocation.visibility = View.VISIBLE
                binding.fpdLocation.text =
                    "${photoDetails.location.city}, ${photoDetails.location.country}"
            } else if (photoDetails.location?.country != null && photoDetails.location?.city == null) {
                binding.fpdLocation.visibility = View.VISIBLE
                binding.fpdLocation.text = photoDetails.location.country
            } else if (photoDetails.location?.country == null && photoDetails.location?.city != null) {
                binding.fpdLocation.visibility = View.VISIBLE
                binding.fpdLocation.text = " ${photoDetails.location.city}"
            } else {
                binding.fpdLocation.visibility = View.GONE
                binding.fpdLocation.movementMethod
            }

            photoDetails.tags?.let {
                adapter.addTags(it)
            }

            binding.fpdClContainer.visibility = View.VISIBLE

            binding.fpdImage.setOnClickListener {
                StfalconImageViewer.Builder(
                    /* context = */
                    requireContext(),
                    /* images = */
                    listOf(photoUrl),
                )
                /* imageLoader = */
                { iv, url ->
                    Glide.with(iv)
                        .load(url)
                        .into(iv)
                }
                    .withHiddenStatusBar(false)
                    .withTransitionFrom(binding.fpdImage)
                    .show()
            }

            binding.fpdProfileImage.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(PHOTO_PROFILE_KEY, photoProfile)
                bundle.putString(USER_NAME_KEY, userName)
                findNavController().navigate(
                    R.id.action_photoDetailsFragment_to_userFragment,
                    bundle,
                    fragmentAnim()
                )
            }
        }

        /**
         * Получение и обновление статистики фото из PhotoDetailsViewModel
         */

        observeData(viewModel.photoStatistics) { photoStatistics ->

            binding.fpdTvNumberOfViewInfo.text = photoStatistics.views.total?.formated
            binding.fpdTvNumberOfDownlandInfo.text = photoStatistics.downloads.total?.formated
        }

        /**
         * Получение и обновление данных о Messages из CollectionViewModel
         */

        observeData(viewModel.messageFlow) { message ->
            when (message) {
                Messages.NetworkIsDisconnected -> {
//                    snackbar(getString(R.string.network_is_disconnected_text))
                    binding.fpdClContainer.visibility = View.GONE
                    binding.fdpDisconnected.visibility = View.VISIBLE
                }

                Messages.AlreadyDownloaded ->
                    showDownloadDialog()

                Messages.NetworkIsConnected ->
                    snackbar(getString(R.string.network_is_connected_text))

                Messages.ShowShimmer -> {
                    binding.fpdShimmerFrameLayout.startShimmer()
                    binding.fpdShimmerFrameLayout.visibility = View.VISIBLE
                }

                Messages.HideShimmer -> {
                    binding.fpdShimmerFrameLayout.stopShimmer()
                    binding.fpdShimmerFrameLayout.visibility = View.GONE
                }
            }
            viewModel.clearMessage()
        }
    }

    /**
     * Проверка на доступ к хранилищу
     */

    private val writeLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.onDownloadClick(photoId)
            } else {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.error_access_text),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.R)
    private val manageLauncher =
        registerForActivityResult(ManageStorageContract()) { isGranted ->
            if (isGranted) {
                viewModel.onDownloadClick(photoId)
            } else {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.error_access_text),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setPhotoId(photoId)
        super.onViewCreated(view, savedInstanceState)

        binding.fpdRvListTags.adapter = adapter
        binding.fpdRvListTags.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        Glide
            .with(view)
            .load(photoUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
//            .override(2, 2)
            .into(binding.fpdImage)

        Glide
            .with(view)
            .load(photoProfile)
            .into(binding.fpdProfileImage)
        binding.fpdUserName.text = userName

        binding.fdpToolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.fpdBtnDownland.setOnClickListener {
            if (isPhotoExists(requireContext(), photoId)) {
                showDownloadDialog()
            } else {
                downloadPhoto()
            }
        }
    }

    /**
     * Загрузка фото на устройство
     */
    private fun downloadPhoto() {
        binding.fpdBtnDownland.setImageResource(R.drawable.baseline_download_24)
        binding.fpdBtnDownland.setPadding(4)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                viewModel.onDownloadClick(photoId)
                Toast.makeText(
                    context,
                    getString(R.string.start_of_the_download_text),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                manageLauncher.launch(Unit)
            }

        } else if (checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.onDownloadClick(photoId)
            Toast.makeText(
                context,
                getString(R.string.start_of_the_download_text),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            writeLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    /**
     * Диалог при повторном скачивании фото
     */
    private fun showDownloadDialog() {
        val dialog = AlertDialog.Builder(requireContext(), R.style.MultiChoiceAlertDialog)
        dialog.setTitle("Скачать снова?")
        dialog.setMessage("Это фото уже загружено. Вы хотите загрузить его еще раз?")
        dialog.setNegativeButton("Нет") { d, w ->
            d.cancel()
        }
        dialog.setPositiveButton("Да") { d, w ->
            downloadPhoto()
        }
        dialog.create()
        dialog.show()
    }
}