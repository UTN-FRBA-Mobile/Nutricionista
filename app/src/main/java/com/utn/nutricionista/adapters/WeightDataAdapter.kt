package com.utn.nutricionista.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.utn.nutricionista.R
import com.utn.nutricionista.models.Weight
import kotlinx.android.synthetic.main.item_weight_row.view.*
import androidx.appcompat.app.AlertDialog
import com.utn.nutricionista.WeightActivity


class WeightDataAdapter(private val myDataset: MutableList<Weight>, private val context : Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val view : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weight_header, parent,false)
            return HeaderViewHolder(view)
        }

        val view : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weight_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {

            holder.itemView.weight_date.text = getItem(position).fecha
            holder.itemView.weight_value.text = getItem(position).peso.toString() + " kg"
            if (position < myDataset.size) {
                holder.itemView.weight_prev_compare.setImageResource(
                    if (getItem(position).peso > getItem(position+1).peso)
                        R.drawable.baseline_arrow_upward_black_18dp else R.drawable.baseline_arrow_downward_black_18dp
                )
            }

            holder.itemView.setOnClickListener { view ->

                Snackbar.make(view, "Highlight Graph", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

            holder.itemView.setOnLongClickListener { view ->
                AlertDialog.Builder(holder.itemView.context, R.style.DialogTheme)
                    .setTitle("Eliminar registro")
                    .setMessage("¿Desea eliminar el registro?")
                    .setPositiveButton("Ok") { dialog, which ->
                        (context as WeightActivity).deleteWeight(getItem(position).id!!)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancelar", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
                return@setOnLongClickListener true
            }
        }
    }

    private fun getItem(position: Int): Weight {
        return myDataset[position - 1]
    }

    override fun getItemCount() = myDataset.size + 1

    override fun getItemViewType(position: Int): Int {
        if (isPositionHeader(position))
            return TYPE_HEADER
        return TYPE_ITEM
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
