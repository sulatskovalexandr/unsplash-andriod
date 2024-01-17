package com.unsplash.sulatskov.ui.user_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.unsplash.sulatskov.R
import com.unsplash.sulatskov.appComponent
import com.unsplash.sulatskov.common.observeData
import com.unsplash.sulatskov.constants.Const
import com.unsplash.sulatskov.databinding.FragmentUserBinding
import com.unsplash.sulatskov.ui.base.BaseFragment

class UserFragment : BaseFragment<UserViewModel, FragmentUserBinding>() {

    /**
     * Подписка на получение и обновление данных из PhotoViewModel
     */
    override val viewModelClass: Class<UserViewModel>
        get() = UserViewModel::class.java

    override fun createViewBinding(): FragmentUserBinding =
        FragmentUserBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

    private val photoProfile: String
        get() = arguments?.getString(Const.PHOTO_PROFILE_KEY) ?: error("error")
    private val userName: String
        get() = arguments?.getString(Const.USER_NAME_KEY) ?: error("error")

    override fun observeViewModel() {

        /**
         * Получение данных о пользователе из UserViewModel
         */
        observeData(viewModel.user) { user ->
            binding.fprQuantityOfPhoto.text = user?.totalPhotos.toString()
            binding.fprQuantityOfLikes.text = user?.totalLikes.toString()
            binding.fprQuantityOfCollection.text = user?.totalCollections.toString()

            if (user?.location != null) {
                binding.fprLocation.text = user.location
                binding.fprLocation.visibility = View.VISIBLE
            }

            if (user?.bio != null) {
                binding.fprDescription.text = user.bio
                binding.fprDescription.visibility = View.VISIBLE
            }

            binding.fprName.text = user?.name

            binding.pfToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            binding.fprProfileImage.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(Const.PHOTO_URL_KEY, user?.profileImage?.large.toString())
                findNavController().navigate(
                    R.id.action_profileFragment_to_photoZoomFragment,
                    bundle
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setUserName(userName)
        super.onViewCreated(view, savedInstanceState)

        binding.fprViewPager.adapter = UserPagerAdapter(
            fm = childFragmentManager,
            userName = userName,
            requireContext()
        )

        Glide
            .with(view)
            .load(photoProfile)
            .thumbnail(
                Glide.with(view)
                    .load(photoProfile)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.error_circle_image)
                    .override(2, 2)
            )
            .into(binding.fprProfileImage)

        binding.pfToolbar.title = userName
    }
}