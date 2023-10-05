package com.example.myapplication.ui.photo_screen.photo_details_screen.photo_zoom_screen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.constants.Const.PHOTO_URL_KEY
import com.example.myapplication.databinding.FragmentPhotoZoomBinding

class PhotoZoomFragment : Fragment() {

    private val photoUrl: String
        get() = arguments?.getString(PHOTO_URL_KEY) ?: error("error")

    private var _binding: FragmentPhotoZoomBinding? = null
    private val binding get() = requireNotNull(_binding)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoZoomBinding.inflate(inflater, container, false)
//        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity?.window?.statusBarColor = Color.alpha(R.color.alpha_0)
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
//        var counter = 0
//        binding.pzfImage.setOnClickListener {
//            counter++
//            if (counter % 2 != 0){
//                activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//            }else{
//                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//
//            }
//        }
    }

    override fun onDestroyView() {
//        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onDestroyView()
    }
}