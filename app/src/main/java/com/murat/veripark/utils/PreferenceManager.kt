package com.murat.veripark.utils

import android.content.Context
import com.murat.veripark.ext.get
import com.murat.veripark.ext.set
import com.murat.veripark.network.responses.HandShakeResponse

class PreferenceManager(context: Context) {
    companion object {
        private const val PREFS = "prefs"
        private const val HANDSHAKE = "handshake"
    }

    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    /**
     * HandShake
     */
    var handShake: HandShakeResponse?
        get() = prefs.get(HANDSHAKE)
        set(value) {
            prefs.set(HANDSHAKE, value)
        }
}