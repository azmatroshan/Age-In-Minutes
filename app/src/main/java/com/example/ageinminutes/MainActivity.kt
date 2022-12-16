package com.example.ageinminutes

import android.app.DatePickerDialog
import android.icu.util.Calendar.*
import android.os.Build

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myBtn = findViewById<Button>(R.id.button)
        myBtn.setOnClickListener {
            clickDatePicker()
        }

    }
    private var selectDate : TextView?= null
    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(YEAR)
        val month = myCalendar.get(MONTH)
        val day = myCalendar.get(DAY_OF_MONTH)
        selectDate = findViewById(R.id.dateSelected)
        val dpd = DatePickerDialog(this,
            { _, yearSelected, monthSelected, daySelected ->
                selectDate?.text = when(true){
                    (daySelected <10 && monthSelected <9) -> "0$daySelected-0${monthSelected+1}-$yearSelected"
                    (daySelected <10) -> "0$daySelected-${monthSelected+1}-$yearSelected"
                    (monthSelected <9) -> "$daySelected-0${monthSelected+1}-$yearSelected"
                    else -> "$daySelected-${monthSelected+1}-$yearSelected"
                }
                val dateChosen = "$daySelected-${monthSelected+1}-$yearSelected"
                val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(dateChosen)
                theDate?.let {
                    val timeInMinuteOfSelectedDate = theDate.time /60000
                    val timeInMinuteOfCurrentDate = (sdf.parse(sdf.format(System.currentTimeMillis()))?.time)?.div(
                        60000
                    )
                    val differenceOfTime = timeInMinuteOfCurrentDate?.minus(timeInMinuteOfSelectedDate)
                    val showAgeInMinute :TextView = findViewById(R.id.calculate)
                    showAgeInMinute.text = differenceOfTime.toString()
                }
                Toast.makeText(this,"Calculated",Toast.LENGTH_SHORT).show()
            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}
