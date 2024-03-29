package com.unsplash.sulatskov.ui.photo_screen.photo_details_screen.photo_zoom_screen

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.unsplash.sulatskov.constants.Const.PHOTO_URL_KEY
import com.unsplash.sulatskov.databinding.FragmentPhotoZoomBinding

class PhotoZoomFragment : Fragment() {

    private val photoUrl: String
        get() = arguments?.getString(PHOTO_URL_KEY) ?: error("error")

    private var _binding: FragmentPhotoZoomBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoZoomBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide
            .with(view)
            .load(photoUrl)
//            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.fpzImage)

        binding.fpzBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onDestroyView()
    }
}