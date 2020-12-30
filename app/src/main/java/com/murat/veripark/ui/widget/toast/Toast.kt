package com.murat.veripark.ui.widget.toast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.murat.veripark.R
import com.murat.veripark.ext.findSuitableParent

/**
 * @user: omer.karaca
 * @date: 2.03.2020
 */

class Toast(
    parent: ViewGroup,
    content: ToastView
) : BaseTransientBottomBar<Toast>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        duration = 3000
        getView().setPadding(0, 0, 0, 0)
    }

    fun setMarging(bottom: Int, top: Int, left: Int, right: Int) {
        val layoutParams = view
        layoutParams.marginBottom.plus(bottom)
        layoutParams.marginEnd.plus(right)
        layoutParams.marginStart.plus(left)
        layoutParams.marginTop.plus(top)
    }

    companion object {
        fun make(
            activity: AppCompatActivity,
            type: ToastView.Type = ToastView.Type.REGULAR,
            title: String? = null,
            description: String? = null,
            actionTitle: String? = null,
            imageView: Int? = null,
            bottom: Int? = null,
            left: Int? = null,
            right: Int? = null,
            action: (() -> Unit)? = null
        ): Toast {
            val view = activity.window.decorView.findViewById(android.R.id.content) as View
            val parent = view.findSuitableParent()
                ?: throw IllegalArgumentException(
                    "No suitable parent found from the given view." +
                            " Please provide a valid view."
                )
            val customView = LayoutInflater.from(view.context).inflate(
                R.layout.layout_toast,
                parent,
                false
            ) as ToastView
            if (bottom != null) {
                customView.rootView.marginBottom.plus(bottom)
            }
            if (right != null) {
                customView.marginEnd.plus(right)
            }
            if (left != null) {
                customView.marginStart.plus(left)
            }
            customView.setType(type)
            title?.let { customView.setTitle(it) }
            description?.let { customView.setDescription(it) }
            imageView?.let { customView.setInfoImageView(it) }

            if (actionTitle != null && action != null) {
                customView.setAction(actionTitle, action)
            }

            return Toast(parent, customView)
        }
    }
}
