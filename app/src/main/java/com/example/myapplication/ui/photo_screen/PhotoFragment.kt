package com.example.myapplication.ui.photo_screen

import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
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
import javax.inject.Inject


class PhotoFragment : Fragment(), ClickListener {

    @Inject
    lateinit var viewModel: PhotoViewModel

    private val adapter = PhotoAdapter(this) // Передача адаптера

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
        binding.fpSrlRefresh.setOnRefreshListener {
            adapter.clear()
            viewModel.onRefreshPhotos()
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

        fun showDialog() {
            val builder = AlertDialog.Builder(activity as MainActivity)
            val inflater = activity?.layoutInflater
            val view = inflater?.inflate(R.layout.order_dialog, null)
            val radioGroup = view?.findViewById<RadioGroup>(R.id.radiogroup)
            val radioStyle = ContextThemeWrapper(radioGroup?.context, R.style.MyRadioButton)
            builder.setView(view)
            val dialog = builder.create()
            radioGroup?.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.rb_order_by_latest -> {
                        adapter.clear()
                        viewModel.onLoadPhotos()
                        dialog.dismiss()
                    }
                    R.id.rb_order_by_oldest -> {
                        adapter.clear()
                        viewModel.loadListOldestPhoto()
                        dialog.dismiss()
                    }
                    R.id.rb_order_by_popular -> {
                        adapter.clear()
                        viewModel.loadListPopularPhoto()
                        dialog.dismiss()
                    }
                }
                dialog.dismiss()

            }
            dialog.show()
        }

        binding.fpToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.item_order) {
                showDialog()
            } else if (it.itemId == R.id.item_search) {

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
        findNavController().navigate(R.id.action_photoFragment_to_photoDetailsFragment, bundle)
    }

    override fun onProfileImageClick(photoProfile: String, userName: String) {
        val bundle = Bundle()
        bundle.putString(PHOTO_PROFILE_KEY, photoProfile)
        bundle.putString(USER_NAME_KEY, userName)
        findNavController().navigate(R.id.action_photoFragment_to_userFragment, bundle)
    }
}