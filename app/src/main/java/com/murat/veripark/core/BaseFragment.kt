package com.murat.veripark.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.murat.veripark.R
import com.murat.veripark.ext.observeEvent
import com.murat.veripark.ui.widget.toast.Toast
import com.murat.veripark.ui.widget.toast.ToastView
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class BaseFragment<B : ViewDataBinding, M : BaseViewModel>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    lateinit var viewBinding: B


    val viewModel: M by lazy {
        getViewModel(
            clazz = viewModelClass(),
            parameters = { parametersOf(arguments()) }
        )
    }

    abstract fun onInitDataBinding()

    abstract fun viewModelClass(): KClass<M>

    private fun arguments(): Bundle {
        val fragmentArgs = arguments ?: Bundle()
        val activityArgs = activity?.intent?.extras ?: Bundle()
        return activityArgs.apply { putAll(fragmentArgs) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return try {
            viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            viewBinding.lifecycleOwner = viewLifecycleOwner
            viewBinding.root
        } finally {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitDataBinding()
        observeEvent(viewModel.baseEvent, ::onViewEvent)
    }

    private fun onViewEvent(event: BaseViewEvent) {
        when (event) {
            BaseViewEvent.ForceLogout -> {

            }

            BaseViewEvent.ShowCommonNetworkError -> showError(R.string.something_went_wrong)

            BaseViewEvent.ShowConnectivityError -> showError(R.string.connectivity_error)

            BaseViewEvent.ShowUserNotFoundError -> {
                showError(R.string.user_not_found_error)

            }
            BaseViewEvent.ShowInternalServerError -> {
                showError(R.string.internal_server_not_found)
            }

            is BaseViewEvent.ShowCustomError -> showError(event.message)
        }
    }


    fun showCommonError() {
        (requireActivity() as? AppCompatActivity)?.let {
            Toast.make(it, ToastView.Type.ERROR, getString(R.string.something_went_wrong))
                .show()
        }
    }

    fun showError(@StringRes error: Int) {
        (requireActivity() as? AppCompatActivity)?.let {
            Toast.make(it, ToastView.Type.ERROR, getString(error)).show()
        }
    }

    fun showError(@StringRes title: Int, @StringRes description: Int) {
        (requireActivity() as? AppCompatActivity)?.let {
            Toast.make(
                it,
                ToastView.Type.ERROR,
                getString(title),
                getString(description)
            ).show()
        }
    }

    fun showMessage(title: String, description: String, type: ToastView.Type) {
        (requireActivity() as? AppCompatActivity)?.let {
            Toast.make(
                it,
                type,
                title,
                description
            ).setDuration(5000).show()
        }
    }

    fun showError(error: String) {
        (requireActivity() as? AppCompatActivity)?.let {
            Toast.make(it, ToastView.Type.ERROR, error).show()
        }
    }
}