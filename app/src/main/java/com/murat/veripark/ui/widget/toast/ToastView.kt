package com.murat.veripark.ui.widget.toast

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.api.load
import com.google.android.material.snackbar.ContentViewCallback
import com.murat.veripark.R
import com.murat.veripark.binding.visible
import com.murat.veripark.databinding.ViewToastBinding
import kotlinx.android.synthetic.main.view_toast.view.*

class ToastView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    enum class Type {
        REGULAR, SUCCESS, WARNING, ERROR;
    }

    private var viewBinding: ViewToastBinding =
        ViewToastBinding.inflate(LayoutInflater.from(context), this, true)

    fun setType(type: Type) {
        viewBinding.root.background = ContextCompat.getDrawable(
            context, when (type) {
                Type.REGULAR -> R.drawable.bg_toast_regular
                Type.SUCCESS -> R.drawable.bg_toast_success
                Type.WARNING -> R.drawable.bg_toast_warning
                Type.ERROR -> R.drawable.bg_toast_error
            }
        )
        val textColor = ContextCompat.getColor(
            context, when (type) {
                Type.REGULAR -> R.color.main_text
                Type.SUCCESS, Type.WARNING, Type.ERROR -> R.color.white
            }
        )

        viewBinding.titleTextView.setTextColor(textColor)
        viewBinding.descriptionTextView.setTextColor(textColor)
        viewBinding.actionButton.setTextColor(textColor)
        viewBinding.actionButton.background = ContextCompat.getDrawable(
            context, when (type) {
                Type.REGULAR -> R.drawable.shape_toast_button_green
                Type.SUCCESS, Type.WARNING, Type.ERROR -> R.drawable.shape_toast_button_white
            }
        )
    }


    fun setTitle(title: String) {
        viewBinding.titleTextView.text = title
        viewBinding.titleTextView.visibility = View.VISIBLE
    }

    fun setDescription(description: String) {
        viewBinding.descriptionTextView.text = description
        viewBinding.descriptionTextView.visibility = View.VISIBLE
    }

    fun setInfoImageView(image: Int) {
        viewBinding.infoImageView.load(image)
        viewBinding.infoImageView.visibility = View.VISIBLE
    }

    fun setAction(title: String, listener: () -> Unit) {
        viewBinding.actionButton.text = title
        viewBinding.actionButton.setOnClickListener { listener.invoke() }
        viewBinding.actionButton.visibility = View.VISIBLE
    }

    fun inflate(
        type: Type = Type.REGULAR,
        title: String? = null,
        description: String? = null,
        actionTitle: String? = null,
        image: Int? = null,
        action: (() -> Unit)? = null
    ) {
        root.background = ContextCompat.getDrawable(
            context, when (type) {
                Type.REGULAR -> R.drawable.bg_toast_regular
                Type.SUCCESS -> R.drawable.bg_toast_success
                Type.WARNING -> R.drawable.bg_toast_warning
                Type.ERROR -> R.drawable.bg_toast_error
            }
        )

        title_text_view.setTextColor(
            ContextCompat.getColor(
                context, when (type) {
                    Type.REGULAR -> R.color.main_text
                    Type.SUCCESS, Type.WARNING, Type.ERROR -> R.color.white
                }
            )
        )

        description_text_view.setTextColor(
            ContextCompat.getColor(
                context, when (type) {
                    Type.REGULAR -> R.color.main_text
                    Type.SUCCESS, Type.WARNING, Type.ERROR -> R.color.white
                }
            )
        )

        action_button.setTextColor(
            ContextCompat.getColor(
                context, when (type) {
                    Type.REGULAR -> R.color.main_text
                    Type.SUCCESS, Type.WARNING, Type.ERROR -> R.color.white
                }
            )
        )

        action_button.background = ContextCompat.getDrawable(
            context, when (type) {
                Type.REGULAR -> R.drawable.shape_toast_button_green
                Type.SUCCESS, Type.WARNING, Type.ERROR -> R.drawable.shape_toast_button_white
            }
        )

        title_text_view.visible = title != null
        description_text_view.visible = description != null
        info_image_view.visible = image != null
        action_button.visible = actionTitle != null && action != null

        title?.let { title_text_view.text = it }
        description?.let { description_text_view.text = it }
        image?.let { info_image_view.setImageResource(it) }
        actionTitle?.let { action_button.text = it }
    }

    override fun animateContentIn(delay: Int, duration: Int) {}

    override fun animateContentOut(delay: Int, duration: Int) {}
}
