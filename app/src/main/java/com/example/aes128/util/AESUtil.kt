package com.example.aes128.util

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtil {

    private const val AES_MODE = "AES/GCM/NoPadding"
    private const val IV_LENGTH = 12
    private const val TAG_LENGTH = 128

    fun encrypt(text: String, key: String): String {
        return try {

            val secretKey =
                SecretKeySpec(key.padEnd(16, '0').toByteArray(), "AES")

            val cipher = Cipher.getInstance(AES_MODE)

            val iv = ByteArray(IV_LENGTH)
            SecureRandom().nextBytes(iv)

            val spec = GCMParameterSpec(TAG_LENGTH, iv)

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)

            val encrypted = cipher.doFinal(text.toByteArray())

            Base64.encodeToString(iv + encrypted, Base64.NO_WRAP)

        } catch (e: Exception) {
            ""
        }
    }

    fun decrypt(cipherText: String, key: String): String {
        return try {

            val decoded = Base64.decode(cipherText, Base64.NO_WRAP)

            if (decoded.size < IV_LENGTH) return ""

            val iv = decoded.copyOfRange(0, IV_LENGTH)
            val encrypted = decoded.copyOfRange(IV_LENGTH, decoded.size)

            val secretKey =
                SecretKeySpec(key.padEnd(16, '0').toByteArray(), "AES")

            val cipher = Cipher.getInstance(AES_MODE)

            val spec = GCMParameterSpec(TAG_LENGTH, iv)

            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

            String(cipher.doFinal(encrypted))

        } catch (e: Exception) {
            ""
        }
    }
}