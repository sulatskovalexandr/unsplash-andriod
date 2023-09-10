package com.example.myapplication.screens.photos_screens.photo_screen.presentation.photo_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Event
import com.example.myapplication.R
import com.example.myapplication.appComponent
import com.example.myapplication.common.Messages
import com.example.myapplication.common.observeData
import com.example.myapplication.common.snackbar
import com.example.myapplication.constants.Const
import com.example.myapplication.constants.Const.PHOTO_PROFILE_KEY
import com.example.myapplication.constants.Const.PHOTO_URL_KEY
import com.example.myapplication.constants.Const.USER_NAME_KEY
import com.example.myapplication.databinding.FragmentPhotoBinding
import com.example.myapplication.screens.photos_screens.photo_details_screen.presentation.photo_details_screen.PhotoDetailsFragment
import com.example.myapplication.screens.photos_screens.photo_screen.domain.model.Photo
import javax.inject.Inject


class PhotoFragment : Fragment(), PhotoListClickListener {

    @Inject
    lateinit var viewModel: PhotoViewModel

    private val adapter = PhotosAdapter(this) // Передача адаптера

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    /**
     * Создание view, сам GeneralFragment уже создан в onCreate
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * View уже точно создоно
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fgRvListPhotos.adapter = adapter // Подключение PhotosAdapter() к fgRvListPhotos
        val layoutManager = LinearLayoutManager(requireContext())
        binding.fgRvListPhotos.layoutManager =
            layoutManager // Настройка отображения fgRvListPhotos (один элемент в строке)


//          Подгрузка данных следующей страницы списка
        binding.fgRvListPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                Log.e("werwerw", "$lastPosition x ${adapter.itemCount}")
                if (lastPosition > (adapter.itemCount - 5)) {
                    Log.e("werwerw", "load")
                    viewModel.onLoadPhotos()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        /**
         *
         */
        observeData(viewModel.photoList) { event ->
            when (event) {
                is Event.Loading -> onProgress()
                is Event.Success -> onSuccess(event.data)
                is Event.Error -> onError()
            }
        }

        /**
         * Обработчик свайпа на обновление данных в списке
         */
        binding.fgSrlRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.onRefreshPhotos()
        }

        observeData(viewModel.messageFlow) { message ->
            if (message is Messages.NetworkIsDisconnected) {
                snackbar(getString(R.string.network_is_disconnected_text))
            }
            viewModel.clearMessage()
        }
    }

    private fun onProgress() {
        binding.fgSrlRefresh.isRefreshing = false
    }

    private fun onSuccess(data: List<Photo>) {
        try {
            adapter.addPhoto(data)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    private fun onError() {
        snackbar(getString(R.string.network_is_disconnected_text))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

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
        val photoDetailsFragment = PhotoDetailsFragment()
        photoDetailsFragment.arguments = bundle
        findNavController().navigate(R.id.action_photoFragment_to_photoDetailsFragment, bundle)
    }
}