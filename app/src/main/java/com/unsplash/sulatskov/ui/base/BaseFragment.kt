package com.unsplash.sulatskov.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import javax.inject.Inject

/**
 * Базовый класс для фрагментов с ViewModel и ViewBinding
 *
 * @property viewModelFactory [ViewModelFactory]
 *
 */
abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory<VM>

    val viewModel: VM by lazy {
        getViewModelInstance()
    }

    abstract val viewModelClass: Class<VM>

    val binding: VB get() = getViewBinding()

    private var _viewBinding: VB? = null

    protected abstract fun createViewBinding(): VB

    private fun getViewBinding(): VB = requireNotNull(_viewBinding)
    abstract fun inject()
    private fun getViewModelInstance(): VM {
        return ViewModelProvider(this as Fragment, viewModelFactory).get(viewModelClass)
    }

    protected open fun observeViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = createViewBinding().also {
            _viewBinding = it
        }
        return binding.root
    }

    /**
     * @see Fragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
        viewModel.onViewCreated()
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}