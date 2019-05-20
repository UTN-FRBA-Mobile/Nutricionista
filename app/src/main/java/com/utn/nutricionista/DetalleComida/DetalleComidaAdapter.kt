package com.utn.nutricionista.DetalleComida

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.utn.nutricionista.DetalleComida.DetalleComidaFragment.OnListFragmentInteractionListener
import com.utn.nutricionista.R
import com.utn.nutricionista.dummy.DummyContent.DummyItem

import kotlinx.android.synthetic.main.fragment_detalle_comida.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class DetalleComidaAdapter(
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<DetalleComidaAdapter.ViewHolder>() {


    var itemsDieta : List<DetalleComida> = ArrayList()

    fun setData(itemsDietaLst : List<DetalleComida>) {
        itemsDieta = itemsDietaLst
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_detalle_comida, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

                //var itemPosition: Int = if(position>0) position -1 else position
                holder.itemView.detalleItemTxt.text = itemsDieta[position].detalle
                if(itemsDieta[position].enPreDef){
                    holder.itemView.statusComidaIcon.setImageResource(R.drawable.icons8_marca_de_verificacion_64)
                }else{
                    holder.itemView.statusComidaIcon.setImageResource(R.drawable.icons8_eliminar_64)
                }

    }

    override fun getItemCount(): Int = itemsDieta.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
