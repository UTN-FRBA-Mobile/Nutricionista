package com.utn.nutricionista

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.EditText

class InputWeightDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var date : EditText
    private lateinit var btnAccept : Button
    private lateinit var btnCancel : Button
    private lateinit var weight : EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view = activity!!.layoutInflater.inflate(R.layout.fragment_input_weight_dialog,null,false)
            builder.setView(view)

            weight = view.findViewById(R.id.input_weight_value) as EditText
            date = view.findViewById(R.id.input_weight_date) as EditText
            date.setOnClickListener { showDatePickerDialog() }

            btnCancel = view.findViewById(R.id.btnInputWeightCancel) as Button
            btnCancel.setOnClickListener { dialog.dismiss()}
            btnAccept = view.findViewById(R.id.btnInputWeightConfirm) as Button
            btnAccept.setOnClickListener { saveNewWeightRecord() }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun saveNewWeightRecord() {
        if (weight.text.isNotEmpty() && date.text.isNotEmpty()) {
            (activity as WeightActivity).saveNewWeightRecord(weight.text.toString().toFloat(), date.text.toString())
            dialog.dismiss()
        }
    }

    fun showDatePickerDialog() {
        val newFragment = DatePickerFragment().newInstance(this)
        newFragment.show(activity!!.supportFragmentManager, "datePicker")
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        //+1 because january is zero
        val monthStr = (month + 1).toString().padStart(2, '0')
        val selectedDate = "$year-$monthStr-$day"
        date.setText(selectedDate)
    }
}

class DatePickerFragment : DialogFragment() {
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
        var dialog = DatePickerDialog(activity!!, listener, year, month, day)
        dialog.datePicker.maxDate = c.timeInMillis
        return dialog
    }
}
