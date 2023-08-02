package com.example.myapplication.main.screens.photo_details_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.constants.Const.PHOTO_ID_KEY
import com.example.myapplication.databinding.FragmentPhotoDetailsBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class PhotoDetailsFragment : Fragment() {
    private val viewModel: PhotoDetailsViewModel by viewModels()

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

        val postId = arguments?.getInt(PHOTO_ID_KEY)


    }

    companion object {
        fun arg(photoId: String): Bundle {
            val bundle = Bundle()
            bundle.putString(PHOTO_ID_KEY, photoId)
            return bundle
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}

inline fun <T> Fragment.observeData(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = viewLifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: (T) -> Unit
) = lifecycleOwner.lifecycleScope.launch {
    lifecycleOwner.repeatOnLifecycle(state) {
        flow.collect { data ->
            block(data)
        }
    }
}
