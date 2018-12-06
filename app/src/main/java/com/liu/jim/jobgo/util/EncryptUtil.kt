package com.liu.jim.jobgo.util
import android.util.Base64
import java.nio.charset.Charset
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

/**
 * Created by VincentLaf on 2017/12/2.
 */
class EncryptUtil {

    companion object {

        //AES密码
        private val ENCRYPT_KEY = "0123456789abcdef"

        //"算法/模式/补码方式"
        private val ALGORITHM_STRING = "AES/ECB/PKCS5Padding"

        //加密密码
        fun encryptPassword(psw: String): String = encryptWithAES(encryptWithMD5(psw))

        //MD5加密 text:需要加密的文本
        fun encryptWithMD5(text: String): String {
            val digest = MessageDigest.getInstance("MD5")
                    .digest(text.toByteArray(Charset.forName("UTF-8")))
            val hex = StringBuffer()
            for (b in digest) {
                //获取二进制后8位有效值
                val i = b.toInt() and 0xFF
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    hexString = "0" + hexString
                }
                hex.append(hexString)
            }
            return hex.toString()
        }

        //AES加密 text:需要加密的文本 encryptKey:密码
        fun encryptWithAES(text: String, encryptKey: String = ENCRYPT_KEY): String {
            return getEncodedBase64(aesEncryptToBytes(text, encryptKey))
        }

        //base64进行编码
        private fun getEncodedBase64(bytes: ByteArray): String {
            return Base64.encodeToString(bytes, Base64.NO_WRAP)
        }

        //AES加密过程
        private fun aesEncryptToBytes(text: String, encryptKey: String): ByteArray {
            val kgen = KeyGenerator.getInstance("AES")
            kgen.init(128)
            val cipher = Cipher.getInstance(ALGORITHM_STRING)
            cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(encryptKey.toByteArray(), "AES"))
            return cipher.doFinal(text.toByteArray())
        }

    }
}