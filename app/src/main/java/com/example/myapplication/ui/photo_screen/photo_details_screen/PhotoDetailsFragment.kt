package com.example.myapplication.ui.photo_screen.photo_details_screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.R
import com.example.myapplication.appComponent
import com.example.myapplication.common.*
import com.example.myapplication.constants.Const.PHOTO_ID_KEY
import com.example.myapplication.constants.Const.PHOTO_PROFILE_KEY
import com.example.myapplication.constants.Const.PHOTO_URL_KEY
import com.example.myapplication.constants.Const.USER_NAME_KEY
import com.example.myapplication.databinding.FragmentPhotoDetailsBinding
import com.example.myapplication.ui.photo_screen.photo_details_screen.photo_zoom_screen.PhotoZoomFragment
import com.example.myapplication.ui.user_screen.UserFragment
import javax.inject.Inject


class PhotoDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModel: PhotoDetailsViewModel
    private val adapter = PhotoDetailsAdapter()

    private var _binding: FragmentPhotoDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val photoUrl: String
        get() = arguments?.getString(PHOTO_URL_KEY) ?: error("error")
    private val photoId: String
        get() = arguments?.getString(PHOTO_ID_KEY) ?: error("error")
    private val photoProfile: String
        get() = arguments?.getString(PHOTO_PROFILE_KEY) ?: error("error")
    private val userName: String
        get() = arguments?.getString(USER_NAME_KEY) ?: error("error")


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            activity?.window?.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
////            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.fpdRvListTags.adapter = adapter
        binding.fpdRvListTags.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewModel.setPhotoId(photoId)


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
            }

            photoDetails.tags?.let {
                adapter.addTags(it)
            }

            binding.fpdClContainer.visibility = View.VISIBLE

            binding.fpdImage.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(PHOTO_URL_KEY, photoDetails.urls?.full)
                val photoZoomFragment = PhotoZoomFragment()
                photoZoomFragment.arguments = bundle
                findNavController().navigate(
                    R.id.action_photoDetailsFragment_to_photoZoomFragment,
                    bundle
                )
            }
        }

        observeData(viewModel.photoStatistics) { photoStatistics ->

            binding.fpdTvNumberOfViewInfo.text = photoStatistics.views.total.formated
            binding.fpdTvNumberOfDownlandInfo.text = photoStatistics.downloads.total.formated
        }

        binding.fdpToolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        fun downloadPhoto() {
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

        fun showDownloadDialog() {
            val dialog = AlertDialog.Builder(requireContext())
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

        binding.fpdBtnDownland.setOnClickListener {
            if (isPhotoExists(requireContext(), photoId)) {
                showDownloadDialog()
            } else {
                downloadPhoto()
            }
        }

        observeData(viewModel.messageFlow) { message ->
            when (message) {
                Messages.NetworkIsDisconnected ->
                    snackbar(getString(R.string.network_is_disconnected_text))

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
                else -> {}
            }
            viewModel.clearMessage()
        }

        binding.fpdProfileImage.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(PHOTO_PROFILE_KEY, photoProfile)
            bundle.putString(USER_NAME_KEY, userName)
            val profileFragment = UserFragment()
            profileFragment.arguments = bundle

            findNavController().navigate(
                R.id.action_photoDetailsFragment_to_userFragment,
                bundle
            )
        }
    }

    override fun onDestroy() {
        _binding = null
//        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onDestroy()
    }
}