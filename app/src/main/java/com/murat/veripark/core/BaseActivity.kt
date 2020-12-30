package com.murat.veripark.core


import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.murat.veripark.R
import com.murat.veripark.ext.observeEvent
import com.murat.veripark.ui.widget.toast.Toast
import com.murat.veripark.ui.widget.toast.ToastView
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass


abstract class BaseActivity<B : ViewDataBinding, M : BaseViewModel>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity() {

    lateinit var viewBinding: B
    val viewModel: M by lazy {
        getViewModel(
            clazz = viewModelClass(),
            parameters = { parametersOf(arguments()) }
        )
    }

    abstract fun onInitDataBinding()

    abstract fun viewModelClass(): KClass<M>

    private fun arguments() = intent.extras ?: Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding.lifecycleOwner = this

        onInitDataBinding()
        observeEvent(viewModel.baseEvent, ::onViewEvent)

    }

    private fun onViewEvent(event: BaseViewEvent) {
        when (event) {
            is BaseViewEvent.ShowCustomError -> showError(event.type, event.message)
        }
    }

    fun showError(error: String, title: String) {
        Toast.make(this, ToastView.Type.ERROR, title = title, description = error).show()
    }

    fun showCommonError() {
        Toast.make(this, ToastView.Type.ERROR, getString(R.string.something_went_wrong))
            .show()

    }

    fun showError(@StringRes error: Int) {
        Toast.make(this, ToastView.Type.ERROR, getString(error)).show()
    }

    fun showError(@StringRes title: Int, @StringRes description: Int) {
        Toast.make(
            this,
            ToastView.Type.ERROR,
            getString(title),
            getString(description)
        ).show()
    }

    fun showMessage(title: String, description: String, type: ToastView.Type) {
        Toast.make(
            this,
            type,
            title,
            description
        ).setDuration(5000).show()
    }
}
