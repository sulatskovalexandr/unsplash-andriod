package com.example.myapplication.ui.login_screen

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Event
import com.example.myapplication.R
import com.example.myapplication.appComponent
import com.example.myapplication.common.CustomTabsHelper
import com.example.myapplication.common.snackbar
import com.example.myapplication.constants.Const.loginUrl
import com.example.myapplication.constants.Const.unsplashAuthCallback
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.base.BaseFragment
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    private var customTabsClient: CustomTabsClient? = null
    private var customTabsSession: CustomTabsSession? = null
    private var shouldUnbindCustomTabService = false

    private val customTabsServiceConnection = object : CustomTabsServiceConnection() {
        override fun onCustomTabsServiceConnected(
            name: ComponentName,
            client: CustomTabsClient
        ) {
            customTabsClient = client
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            customTabsClient = null
        }
    }

    override val viewModelClass: Class<LoginViewModel>
        get() = LoginViewModel::class.java

    override fun createViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun inject() {
        appComponent.inject(this)
    }

//    override fun observeViewModel() {
//        observeData(viewModel.token) {
//
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupCustomTabs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.authButton.setOnClickListener { openUnsplashLoginTab() }

    }


    override fun onDestroy() {
        super.onDestroy()
//        if (shouldUnbindCustomTabService) {
//            unbindService(customTabsServiceConnection)
//            shouldUnbindCustomTabService = false
//        }
        customTabsClient = null
        customTabsSession = null
    }

    fun onNewIntent(intent: Intent?) {
        intent?.data?.let { uri ->
            if (uri.authority.equals(unsplashAuthCallback)) {
                uri.getQueryParameter("code").let { code ->
                    viewModel.login(code.toString())
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.token.collect { event ->
                                when (event) {
                                    is Event.Loading -> onProgress()
                                    is Event.Success -> onSuccess(event.data)
                                    is Event.Error -> onError()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onProgress() {
    }

    private fun onSuccess(data: String) {
        viewModel.saveToken(data)
        findNavController().navigate(R.id.userFragment)
    }

    private fun onError() {
        snackbar(getString(R.string.network_is_disconnected_text))
    }

    private fun setupCustomTabs() {
        CustomTabsHelper.getPackageNameToUse(requireContext())?.let { customTabsPackageName ->
            if (CustomTabsClient.bindCustomTabsService(
                    requireContext(), customTabsPackageName, customTabsServiceConnection
                )
            ) {
                shouldUnbindCustomTabService = true
                customTabsClient?.warmup(0)
                customTabsSession = customTabsClient?.newSession(CustomTabsCallback())?.apply {
                    mayLaunchUrl(
                        Uri.parse(loginUrl),
                        null,
                        mutableListOf(
                            Bundle().apply {
                                putParcelable(
                                    CustomTabsService.KEY_URL,
                                    Uri.parse(getString(R.string.unsplash_join_url))
                                )
                            }
                        )
                    )
                }
            }
        }
    }

    private fun openUnsplashLoginTab() = openCustomTab(loginUrl)

    private fun openCustomTab(uriString: String) {
        CustomTabsHelper.openCustomTab(requireContext(), Uri.parse(uriString))
    }
}