package com.example.myapplication.ui.photo_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.myapplication.Event
import com.example.myapplication.MainActivity
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
import com.example.myapplication.domain.model.Photo
import com.example.myapplication.ui.base.BaseFragment


class PhotoFragment : BaseFragment<PhotoViewModel, FragmentPhotoBinding>(), PhotoClickListener {

    override val viewModelClass: Class<PhotoViewModel>
        get() = PhotoViewModel::class.java

    private val adapter = PhotoAdapter(this) // Передача адаптера
    var checkedItemId = 0

    override fun createViewBinding(): FragmentPhotoBinding =
        FragmentPhotoBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    override fun observeViewModel() {

        observeData(viewModel.photoList) { event ->
            when (event) {
                is Event.Loading -> onProgress()
                is Event.Success -> onSuccess(event.data)
                is Event.Error -> onError()
            }
        }

        observeData(viewModel.messageFlow) { message ->
            when (message) {
                is Messages.NetworkIsDisconnected ->
                    snackbar(getString(R.string.network_is_disconnected_text))
                is Messages.ShowShimmer -> {
                    binding.fpShimmerFrameLayout.startShimmer()
                    binding.fpShimmerFrameLayout.visibility = View.VISIBLE
                }
                is Messages.HideShimmer -> {
                    binding.fpShimmerFrameLayout.stopShimmer()
                    binding.fpShimmerFrameLayout.visibility = View.GONE
                }
                else -> {
                }
            }
            viewModel.clearMessage()
        }
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
        findNavController().navigate(R.id.action_photoFragment_to_photoDetailsFragment, bundle)
    }

    override fun onProfileImageClick(photoProfile: String, userName: String) {
        val bundle = Bundle()
        bundle.putString(PHOTO_PROFILE_KEY, photoProfile)
        bundle.putString(USER_NAME_KEY, userName)
        findNavController().navigate(R.id.action_photoFragment_to_userFragment, bundle)
    }

    /**
     * View уже точно создоно
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fpRvListPhotos.adapter = adapter // Подключение PhotosAdapter() к fgRvListPhotos
        val layoutManager = LinearLayoutManager(requireContext())
        binding.fpRvListPhotos.layoutManager =
            layoutManager // Настройка отображения fgRvListPhotos (один элемент в строке)

//          Подгрузка данных следующей страницы списка
        binding.fpRvListPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                Log.e("werwerw", "$lastPosition x ${adapter.itemCount}")
                if (lastPosition > (adapter.itemCount - 5)) {
                    Log.e("werwerw", "load")
                    viewModel.onLoadPhotos()
                    (binding.fpRvListPhotos.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                        false
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        /**
         * Обработчик свайпа на обновление данных в списке
         */

        binding.fpSrlRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.onRefreshPhotos()
        }

        fun showDialog() {
            AlertDialog.Builder(activity as MainActivity, R.style.MultiChoiceAlertDialog)
                .setTitle(R.string.order_dialog_text)
                .setSingleChoiceItems(R.array.sort, checkedItemId) { d, checkedId ->
                    when (checkedId) {
                        0 -> {
                            checkedItemId = 0
                            adapter.clear()
                            viewModel.onLoadPhotos()
                            d.dismiss()
                        }
                        1 -> {
                            checkedItemId = 1
                            adapter.clear()
                            viewModel.loadListOldestPhoto()
                            d.dismiss()
                        }
                        2 -> {
                            checkedItemId = 2
                            adapter.clear()
                            viewModel.loadListPopularPhoto()
                            d.dismiss()
                        }
                    }
                }.create()
                .show()
//            val viewAlertDialog = layoutInflater.inflate(R.layout.order_dialog, null)
//            val radioGroup = viewAlertDialog.findViewById<RadioGroup>(R.id.radioGroup)
//            builder.setView(viewAlertDialog)
//            val dialog = builder.create()
//            radioGroup?.setOnCheckedChangeListener { group, checkedId ->
//                when (checkedId) {
//                    R.id.rbOrderByLatest -> {
//                        radioGroup.findViewById<RadioButton>(R.id.rbOrderByLatest).isChecked = true
//                        adapter.clear()
//                        viewModel.onLoadPhotos()
//                        dialog.dismiss()
//                    }
//                    R.id.rbOrderByOldest -> {
//                        radioGroup.findViewById<RadioButton>(R.id.rbOrderByOldest).isChecked = true
//                        adapter.clear()
//                        viewModel.loadListOldestPhoto()
//                        dialog.dismiss()
//                    }
//                    R.id.rbOrderByPopular -> {
//                        radioGroup.findViewById<RadioButton>(R.id.rbOrderByPopular).isChecked = true
//                        adapter.clear()
//                        viewModel.loadListPopularPhoto()
//                        dialog.dismiss()
//                    }
//                }
//                dialog.dismiss()
//            }
//            dialog.show()
        }

        binding.fpToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.item_order) {
                showDialog()
            } else if (it.itemId == R.id.item_search) {
                findNavController().navigate(R.id.action_photoFragment_to_searchFragment)
            }
            return@setOnMenuItemClickListener false
        }
    }

    private fun onProgress() {
        binding.fpSrlRefresh.isRefreshing = false
    }

    private fun onSuccess(data: List<Photo>) {
        try {
            binding.fpRvListPhotos.visibility = View.VISIBLE
            adapter.addPhoto(data)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    private fun onError() {
        snackbar(getString(R.string.network_is_disconnected_text))
    }
}