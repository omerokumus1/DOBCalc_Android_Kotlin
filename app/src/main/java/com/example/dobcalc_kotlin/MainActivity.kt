package com.example.dobcalc_kotlin

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var selectedDateTextView: TextView? = null
    private var inMinutesTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectedDateTextView = findViewById(R.id.selectedDateTextView)
        inMinutesTextView = findViewById(R.id.inMinutesTextView)
        val datePickerBtn: Button = findViewById(R.id.datePickerBtn)
        datePickerBtn.setOnClickListener {
            onClickDatePickerBtn()
        }
    }

    private fun onClickDatePickerBtn() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = createDatePickerDialog(year, month, day)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }

    private fun createDatePickerDialog(
        year: Int,
        month: Int,
        day: Int
    ) = DatePickerDialog(
        this,
        { _, selectedYear, selectedMonth, selectedDay ->

            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            selectedDateTextView?.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate = sdf.parse(selectedDate)
            val selectedDataInMinutes = theDate?.time?.div(60000)

            val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
            val currentDateInMinutes = currentDate?.time?.div(60000)

            val differenceInMinutes = selectedDataInMinutes?.let { currentDateInMinutes?.minus(it) }

            inMinutesTextView?.text = differenceInMinutes.toString()

        }, year, month, day
    )
}