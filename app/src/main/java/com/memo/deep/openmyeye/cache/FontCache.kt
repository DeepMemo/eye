package com.memo.deep.openmyeye.cache

import android.content.Context
import android.graphics.Typeface
import java.util.*

object FontCache {

    private val fontCache = HashMap<String, Typeface>()

    fun getTypeface(fontname: String, context: Context): Typeface? {
        var typeface: Typeface? = fontCache[fontname]

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.assets, fontname)
            } catch (e: Exception) {
                return null
            }

            fontCache[fontname] = typeface
        }

        return typeface
    }
}

