package com.example.myapplication.general_screen.presentation.general_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Event
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.appComponent
import com.example.myapplication.common.observeData
import com.example.myapplication.common.snackbar
import com.example.myapplication.constants.Const
import com.example.myapplication.databinding.FragmentGeneralBinding
import com.example.myapplication.general_screen.domain.model.Photo
import javax.inject.Inject


class GeneralFragment : Fragment(), PhotoListClickListener {

    @Inject
    lateinit var viewModel: GeneralViewModel

    private val adapter = PhotosAdapter(this) // Передача адаптера

    private var _binding: FragmentGeneralBinding? = null
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
        _binding = FragmentGeneralBinding.inflate(inflater, container, false)

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

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0) {
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition > (adapter.itemCount - 10)) {
                        viewModel.onLoadPhotos()
                    }
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
    }

    fun onProgress() {
        binding.fgSrlRefresh.isRefreshing = false
    }

    fun onSuccess(data: List<Photo>) {
        try {
            adapter.addPhoto(data)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun onError() {
        snackbar(getString(R.string.error_text))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onPhotoClick(photoId: String, photoUrl: String) {
        arguments = Bundle().apply {
            putString(Const.PHOTO_ID_KEY, photoId)
            (activity as? MainActivity)?.openPhotoDetailsScreen(photoId, photoUrl)

        }
    }
}