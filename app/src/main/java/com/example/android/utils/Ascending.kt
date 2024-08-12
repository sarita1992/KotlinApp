package com.example.android.utils

import com.example.android.model.BookData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Ascending : Comparator<BookData?> {


    override fun compare(p0: BookData?, p1: BookData?): Int {
        val format = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        try {

            val date1: Date? = format.parse(p0!!.release_date.toString())
            val date2: Date? = format.parse(p1!!.release_date.toString())
            return if (date2 != null) {

                if (date1!!.time < date2.time) 1 else -1
            } else {
                0 // Return 0 to leave it at current index
            }
        } catch (exception: java.lang.Exception) {
            exception.printStackTrace()
        }

        return 0
    }
}
