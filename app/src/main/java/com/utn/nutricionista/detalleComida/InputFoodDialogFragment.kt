package com.utn.nutricionista.detalleComida

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.utn.nutricionista.R


class InputFoodDialogFragment : DialogFragment() {

    private lateinit var btnAccept : Button
    private lateinit var btnCancel : Button
    private lateinit var foodName : EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view = activity!!.layoutInflater.inflate(R.layout.fragment_input_comida_dialog,null,false)
            builder.setView(view)
                .setTitle("Nuevo registro")

            foodName = view.findViewById(R.id.input_food_value) as EditText

            btnCancel = view.findViewById(R.id.btnInputFoodCancel) as Button
            btnCancel.setOnClickListener { dialog.dismiss()}
            btnAccept = view.findViewById(R.id.btnInputFoodConfirm) as Button
            btnAccept.setOnClickListener { saveNewFoodRecord() }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun saveNewFoodRecord() {
        if (foodName.text.isNotEmpty()) {
            (activity as DetalleComidaActivity).saveNewFoodRecord(foodName.text.toString())
            dialog.dismiss()
        }
    }

}