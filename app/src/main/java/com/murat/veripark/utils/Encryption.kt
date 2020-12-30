package com.murat.veripark.utils

import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.spec.AlgorithmParameterSpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class AESUtil(private val aesKey: String, private val aesIv: String) {
    private val aesKeyBytes: ByteArray = Base64.getMimeDecoder().decode(aesKey)
    private val ivKeyBytes: ByteArray = Base64.getMimeDecoder().decode(aesIv)

    @Throws(Exception::class)
    fun aesEncrypt(data: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val secretKeySpec: Key = SecretKeySpec(aesKeyBytes, "AES")
        val algorithmParameterSpec: AlgorithmParameterSpec = IvParameterSpec(ivKeyBytes)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, algorithmParameterSpec)
        return Base64.getMimeEncoder().encodeToString(cipher.doFinal(data.toByteArray()))
    }

    @Throws(Exception::class)
    fun aesDecrypt(data: String?): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val secretKeySpec: Key = SecretKeySpec(aesKeyBytes, "AES")
        val algorithmParameterSpec: AlgorithmParameterSpec = IvParameterSpec(ivKeyBytes)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, algorithmParameterSpec)
        val decodedData = cipher.doFinal(Base64.getMimeDecoder().decode(data))
        return String(decodedData, StandardCharsets.UTF_8)
    }

}