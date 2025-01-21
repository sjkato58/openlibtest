package com.mtfuji.sakura.openlibtest.utils.extractor

import android.content.Context
import java.io.InputStream

class ExtractJsonFromAssetsImpl(
    private val context: Context
): ExtractJsonFromAssets {

    override fun execute(fileName: String): String {
        return try {
            getJsonFromAssets(fileName)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }
    }

    private fun getJsonFromAssets(fileName: String): String {
        val inputStream: InputStream = context.assets.open(fileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }
}