package com.atech.base.util

import android.annotation.SuppressLint
import java.lang.NullPointerException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class DateFormater {
    companion object {
        @JvmStatic
        @SuppressLint("SimpleDateFormat")
        fun changeDateFormat(
            dateInput: String?
        ): String {

            try {
                dateInput.let {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
                    val outputFormat = SimpleDateFormat(
                        "EEEE,  MMM dd, yyyy", Locale.ENGLISH
                    )
                    val date: Date
                    date = inputFormat.parse(dateInput!!)!!

                    return outputFormat.format(date)
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            } catch (e: NullPointerException){
                e.printStackTrace()
            }

            return ""
        }

        @JvmStatic
        @SuppressLint("SimpleDateFormat")
        fun changeDateTimeFormat(
            dateInput: String?
        ): String {

            try {
                dateInput.let {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    val outputFormat = SimpleDateFormat(
                        "EEEE,  MMM dd, yyyy", Locale.ENGLISH
                    )
                    val date: Date
                    date = inputFormat.parse(dateInput!!)!!

                    return outputFormat.format(date)
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return ""
        }

    }

}
