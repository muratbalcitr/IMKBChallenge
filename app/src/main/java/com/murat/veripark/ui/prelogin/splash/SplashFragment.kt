package com.murat.veripark.ui.prelogin.splash

import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.murat.veripark.R
import com.murat.veripark.core.BaseFragment
import com.murat.veripark.data.DeviceInfo
import com.murat.veripark.databinding.FragmentSplashBinding
import com.murat.veripark.ext.observeEvent
import com.murat.veripark.ui.main.MainActivity
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    layoutId = R.layout.fragment_splash
) {
    var keyGenerator: KeyGenerator? = null
    var secretKey: SecretKey? = null

    override fun viewModelClass() = SplashViewModel::class

    @SuppressLint("HardwareIds")
    override fun onInitDataBinding() {

        viewBinding.viewModel = viewModel
        observeEvent(viewModel.event, ::onViewEvent)
        viewModel.handShake(
            DeviceInfo(
                deviceId = Settings.Secure.getString(
                    requireContext().contentResolver,
                    Settings.Secure.ANDROID_ID
                ),
                systemVersion = Build.VERSION.SDK_INT.toString(),
                platformName = "Android",
                deviceModel = Build.MODEL,
                manifacturer = Build.MANUFACTURER
            )
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun onViewEvent(event: SplashViewEvent) {
        when (event) {

            is SplashViewEvent.HandShakeDecoder -> {
                startActivity(MainActivity.newIntent(requireContext())).apply { requireActivity().finish() }
            }

            SplashViewEvent.NetworkError -> showCommonError()

        }
    }
}
