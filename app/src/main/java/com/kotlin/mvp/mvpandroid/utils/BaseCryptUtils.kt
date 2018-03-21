package com.kotlin.mvp.mvpandroid.utils

import android.util.Base64

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

/**
 * @author : hafiq on 15/02/2017.
 */

class BaseCryptUtils @Inject
constructor() {

    private val UTF8 = "UTF-8"
    private val PADDING = "AES/CBC/PKCS5PADDING"
    private val TYPE = "AES"

    fun decodeStringWithIteration(encoded: String): String {
        try {
            var dataDec = encoded.toByteArray(charset(UTF8))
            for (x in 0..3) {
                dataDec = Base64.decode(dataDec, Base64.NO_WRAP)
            }

            return String(dataDec)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun AesCrypt(key: String, iv: String, data: String): String? {
        var markKey = key
        try {
            val CIPHER_KEY_LEN = 16
            if (markKey.length < CIPHER_KEY_LEN) {
                val numPad = CIPHER_KEY_LEN - markKey.length

                val keyBuilder = StringBuilder(markKey)
                for (i in 0 until numPad) {
                    keyBuilder.append("0")
                }
                markKey = keyBuilder.toString()

            } else if (markKey.length > CIPHER_KEY_LEN) {
                markKey = markKey.substring(0, CIPHER_KEY_LEN) //truncate to 16 bytes
            }


            val initVector = IvParameterSpec(iv.toByteArray(charset(UTF8)))
            val skeySpec = SecretKeySpec(markKey.toByteArray(charset(UTF8)), TYPE)

            val cipher = Cipher.getInstance(PADDING)
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initVector)

            val encryptedData = cipher.doFinal(data.toByteArray())

            val base64EncryptedData = Base64.encodeToString(encryptedData, Base64.DEFAULT)
            val base64IV = Base64.encodeToString(iv.toByteArray(charset(UTF8)), Base64.DEFAULT)

            return Base64.encodeToString("$base64EncryptedData:$base64IV".toByteArray(charset(UTF8)), Base64.DEFAULT)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }

    fun AesDecrypt(key: String, data: String): String? {
        var markKey = key
        try {

            val CIPHER_KEY_LEN = 16
            if (markKey.length < CIPHER_KEY_LEN) {
                val numPad = CIPHER_KEY_LEN - markKey.length

                val keyBuilder = StringBuilder(markKey)
                for (i in 0 until numPad) {
                    keyBuilder.append("0")
                }
                markKey = keyBuilder.toString()

            } else if (markKey.length > CIPHER_KEY_LEN) {
                markKey = markKey.substring(0, CIPHER_KEY_LEN) //truncate to 16 bytes
            }

            val parts = String(Base64.decode(data, Base64.DEFAULT)).split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val iv = IvParameterSpec(Base64.decode(parts[1], Base64.DEFAULT))
            val skeySpec = SecretKeySpec(markKey.toByteArray(charset(UTF8)), TYPE)

            val cipher = Cipher.getInstance(PADDING)
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)

            val decodedEncryptedData = Base64.decode(parts[0], Base64.DEFAULT)

            val original = cipher.doFinal(decodedEncryptedData)

            return String(original)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }
}

