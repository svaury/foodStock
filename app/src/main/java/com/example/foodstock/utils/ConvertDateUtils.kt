package com.example.foodstock.utils

import java.text.SimpleDateFormat
import java.util.*

class ConvertDateUtils {

    companion object {


        val dateFormat = "dd/MM/yyyy";
        val simpleDateFormat = SimpleDateFormat (dateFormat);

        fun convertMilliSecondsToFormattedDate(milliSeconds: String): String{
            val calendar = Calendar.getInstance();
            calendar.timeInMillis = milliSeconds.toLong()
            return simpleDateFormat.format(calendar.getTime());
        }

        fun convertDateToMillis(peremptionDate: String): Long {
            val date = simpleDateFormat.parse(peremptionDate)
            val cal = Calendar.getInstance()
            cal.time = date
            return cal.timeInMillis
        }


    }
}

