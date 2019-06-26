package com.utn.nutricionista

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.EditText

class InputWeightDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view = activity!!.layoutInflater.inflate(R.layout.fragment_input_weight_dialog,null,false)
            builder.setView(view)

            val date = view.findViewById(R.id.input_weight_date) as EditText
            date.setOnClickListener { showDatePickerDialog() }
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun showDatePickerDialog() {
            val newFragment = DatePickerFragment()
//                DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
//                    // +1 because january is zero
//                    val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
//                    .setText(selectedDate)
//                })

        newFragment.show(activity!!.supportFragmentManager, "datePicker")
    }
}

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private var listener: DatePickerDialog.OnDateSetListener? = null

    fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragment {
        val fragment = DatePickerFragment()
        fragment.setListener(listener)
        return fragment
    }

    fun setListener(listener: DatePickerDialog.OnDateSetListener) {
        this.listener = listener
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
    }
}
