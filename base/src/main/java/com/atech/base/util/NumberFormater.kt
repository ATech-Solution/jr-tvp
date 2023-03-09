package com.atech.base.util

import java.text.NumberFormat
import java.util.*

/**
 * Created by Abraham Lay on 26/04/20.
 */
open class NumberFormater {
    companion object {
        fun usFormat(value: Int?): String {
            try {
                value?.let {
                    return NumberFormat.getIntegerInstance(Locale.US).format(it)
                }
            } catch (exception: NumberFormatException) {
                return ""
            }
            return ""
        }
    }
}