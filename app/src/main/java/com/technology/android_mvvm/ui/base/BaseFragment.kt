package com.technology.android_mvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.technology.android_mvvm.common.extensions.hideSoftKeyboard
import com.technology.android_mvvm.ui.common.Toaster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment(), BaseFragmentCallbacks {

    @Inject
    lateinit var toaster: Toaster

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    private var _binding: ViewBinding? = null

    private var _viewModel: ViewModel? = null

    @Suppress("UNCHECKED_CAST")
    private val classVM =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>

    @Suppress("UNCHECKED_CAST")
    val binding: VB
        get() = _binding as VB

    @Suppress("UNCHECKED_CAST")
    val viewModel: VM
        get() = _viewModel as VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this as? BaseFragmentCallbacks)?.let { initViewModel() }
    }

    @CallSuper
    override fun initViewModel() {
        _viewModel = createViewModelLazy(classVM.kotlin, { viewModelStore }).value
    }

    override fun setupView() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingInflater.invoke(inflater, container, false).apply {
            _binding = this
        }.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (this as? BaseFragmentCallbacks)?.let {
            setupView()
            bindViewEvents()
            bindViewModel()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @CallSuper
    override fun bindViewEvents() {
        requireNotNull(view).setOnClickListener {
            requireActivity().hideSoftKeyboard()
        }
    }

    protected inline infix fun <T> Flow<T>.bindTo(crossinline action: (T) -> Unit) {
        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    collect { action(it) }
                }
            }
        }
    }
}