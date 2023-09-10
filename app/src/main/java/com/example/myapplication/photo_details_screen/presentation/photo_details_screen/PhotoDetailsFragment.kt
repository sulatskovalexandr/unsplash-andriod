package com.example.myapplication.photo_details_screen.presentation.photo_details_screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.appComponent
import com.example.myapplication.common.ManageStorageContract
import com.example.myapplication.common.formated
import com.example.myapplication.common.observeData
import com.example.myapplication.databinding.FragmentPhotoDetailsBinding
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

    private val writeLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.onDownloadClick(photoId)
            } else {
                Toast.makeText(requireActivity(), "Ошибка загрузки", Toast.LENGTH_SHORT).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.R)
    private val manageLauncher =
        registerForActivityResult(ManageStorageContract()) { isGranted ->
            if (isGranted) {
                viewModel.onDownloadClick(photoId)
            } else {
                Toast.makeText(requireActivity(), "Ошибка загрузки", Toast.LENGTH_SHORT).show()
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
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        return binding.root
    }

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

        observeData(viewModel.photoDetails) { photoDetails ->
            Glide
                .with(view)
                .load(photoDetails.user?.profileImage?.medium)
                .into(binding.fpdProfileImage)
            binding.fpdUserName.text = photoDetails.user?.name
            if (photoDetails.exif?.model != null) {
                binding.fpdTvCameraInfo.text = photoDetails.exif?.model
            } else {
                binding.fpdTvCameraInfo.text = "Неизвестно"
            }
            if (photoDetails.exif?.focalLength != null) {
                binding.fpdTvFocusInfo.text = "${photoDetails.exif.focalLength}mm"
            } else {
                binding.fpdTvFocusInfo.text = "Неизвестно"
            }
            if (photoDetails.exif?.iso != null) {
                binding.fpdTvIsoInfo.text = photoDetails.exif.iso.toString()
            } else {
                binding.fpdTvIsoInfo.text = "Неизвестно"
            }
            if (photoDetails.exif?.aperture != null) {
                binding.fpdTvApertureInfo.text = "f/${photoDetails.exif?.aperture}"
            } else {
                binding.fpdTvApertureInfo.text = "Неизвестно"
            }
            if (photoDetails.exif?.exposition != null) {
                binding.fpdTvExpositionInfo.text = "${photoDetails.exif?.exposition}s"
            } else {
                binding.fpdTvExpositionInfo.text = "Неизвестно"
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
            binding.fdpProgress.visibility = View.GONE
            binding.fpdClContainer2.visibility = View.VISIBLE
        }

        observeData(viewModel.photoStatistics) { photoStatistics ->

            binding.fpdTvNumberOfViewInfo.text = photoStatistics.views.total.formated
            binding.fpdTvNumberOfDownlandInfo.text = photoStatistics.downloads.total.formated
        }

        binding.fpdBtnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        binding.fpdBtnDownland.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    viewModel.onDownloadClick(photoId)
                    Toast.makeText(context, "Загрузка началась", Toast.LENGTH_SHORT).show()
                } else {
                    manageLauncher.launch(Unit)
                }

            } else if (checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                viewModel.onDownloadClick(photoId)
                Toast.makeText(context, "Загрузка началась", Toast.LENGTH_SHORT).show()
            } else {
                writeLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onDestroy()
    }

    companion object {
        private const val PHOTO_ID_KEY = "PHOTO_ID_KEY"
        private const val PHOTO_URL_KEY = "PHOTO_URL_KEY"

        fun createArgs(photoId: String, photoUrl: String) = Bundle(2).apply {
            putString(PHOTO_ID_KEY, photoId)
            putString(PHOTO_URL_KEY, photoUrl)
        }

        fun create(photoId: String, photoUrl: String) = PhotoDetailsFragment().apply {
            arguments = createArgs(photoId, photoUrl)
        }
    }
}