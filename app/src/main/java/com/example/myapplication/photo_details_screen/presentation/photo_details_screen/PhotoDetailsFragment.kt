package com.example.myapplication.photo_details_screen.presentation.photo_details_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.common.formated
import com.example.myapplication.common.observeData
import com.example.myapplication.constants.Const.PHOTO_ID_KEY
import com.example.myapplication.databinding.FragmentPhotoDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailsFragment : Fragment() {
    private val viewModel: PhotoDetailsViewModel by viewModels()
    private val adapter = PhotoDetailsAdapter()

    private var _binding: FragmentPhotoDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fpdRvListTags.adapter = adapter
        binding.fpdRvListTags.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val photoId = arguments?.getString(PHOTO_ID_KEY) ?: error("error")
        viewModel.setPhotoId(photoId)

        observeData(viewModel.photoDetails) { photoDetails ->
            Glide
                .with(view)
                .load(photoDetails.urls?.regular)
                .into(binding.fpdImage)

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
                binding.fpdLocation.text =
                    "${photoDetails.location.city}, ${photoDetails.location.country}"
            } else if (photoDetails.location?.country != null && photoDetails.location?.city == null) {
                binding.fpdLocation.text = photoDetails.location.country
            } else if (photoDetails.location?.country == null && photoDetails.location?.city != null) {
                binding.fpdLocation.text = " ${photoDetails.location.city}"
            } else {
                binding.fpdLocation.visibility = View.GONE
            }
            photoDetails.tags?.let {
                adapter.addTags(it)
            }
        }

        observeData(viewModel.photoStatistics) { photoStatistics ->
            binding.fpdTvNumberOfViewInfo.text = photoStatistics.views.total.formated

            binding.fpdTvNumberOfDownlandInfo.text = photoStatistics.downloads.total.formated
//            binding.fpdTvNumberOfLikesInfo.text = photoStatistics.likes?.total.toString()
        }

        binding.fpdToolbar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}


