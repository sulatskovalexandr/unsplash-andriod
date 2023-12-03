package com.example.myapplication.ui.user_screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.appComponent
import com.example.myapplication.common.observeData
import com.example.myapplication.constants.Const
import com.example.myapplication.databinding.FragmentUserBinding
import com.example.myapplication.ui.base.BaseFragment

class UserFragment : BaseFragment<UserViewModel, FragmentUserBinding>() {

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

            binding.fprTabLayout.getTabAt(0)?.text = getString(R.string.pf_text_photo)
            binding.fprTabLayout.getTabAt(1)?.text = getString(R.string.pf_text_collection)

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
            userName = userName
        )

        Glide
            .with(view)
            .load(photoProfile)
            .into(binding.fprProfileImage)

        binding.pfToolbar.title = userName
    }
}