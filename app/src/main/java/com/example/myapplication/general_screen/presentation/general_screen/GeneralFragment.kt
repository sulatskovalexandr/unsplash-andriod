package com.example.myapplication.general_screen.presentation.general_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.common.observeData
import com.example.myapplication.databinding.FragmentGeneralBinding


class GeneralFragment : Fragment(), PhotoListClickListener {

    private val viewModel: GeneralViewModel by viewModels()

    private val adapter = PhotosAdapter(this) // Передача адаптера

    private var _binding: FragmentGeneralBinding? = null
    private val binding get() = requireNotNull(_binding)

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

        /**
         * Подгрузка данных следующей страницы списка
         */
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
        observeData(viewModel.photoList) { photos ->
            binding.fgSrlRefresh.isRefreshing = false
            photos?.let {
                adapter.addPhoto(it)
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onPhotoClick(photoId: String) {
        (activity as? MainActivity)?.openPhotoDetailsScreen(photoId)
    }
}