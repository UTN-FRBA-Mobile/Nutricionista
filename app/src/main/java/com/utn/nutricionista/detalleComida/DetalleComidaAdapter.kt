package com.utn.nutricionista.detalleComida

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


import com.utn.nutricionista.detalleComida.DetalleComidaFragment.OnListFragmentInteractionListener
import com.utn.nutricionista.R
import com.utn.nutricionista.dummy.DummyContent.DummyItem
import com.utn.nutricionista.models.Comida

import kotlinx.android.synthetic.main.fragment_detalle_comida.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class DetalleComidaAdapter(
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<DetalleComidaAdapter.ViewHolder>() {

    val DIETA_PREDEF = 1
    val FUERA_DIETA_PREDEF = 2
    val CROSS_IMG = R.drawable.icons8_eliminar_64
    val SUCCESS_IMG = R.drawable.icons8_marca_de_verificacion_64

    var itemsDieta : List<Comida> = ArrayList()
    var tipoDieta : Int = 0

    fun setData(itemsDietaLst : List<Comida>, tipoDieta: Int) {
        itemsDieta = itemsDietaLst
        this.tipoDieta = tipoDieta
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_detalle_comida, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.detalleItemTxt.text = itemsDieta[position].nombreComida.capitalize()
        if(tipoDieta == DIETA_PREDEF){
            val comidaIcon = holder.itemView.statusComidaIcon
            if (itemsDieta[position].realizada) {
                comidaIcon.setImageResource(SUCCESS_IMG)
                comidaIcon.tag = SUCCESS_IMG
            } else {
                comidaIcon.setImageResource(CROSS_IMG)
                comidaIcon.tag = CROSS_IMG
            }
            holder.itemView.statusComidaIcon.setOnClickListener{
                checkEstadoComida(it)
            }
        }
    }

    fun checkEstadoComida(view: View){
        when(view.tag){ //TODO: El guardado
            SUCCESS_IMG -> {
                view.statusComidaIcon.setImageResource(CROSS_IMG)
                view.tag = CROSS_IMG
            }
            CROSS_IMG -> {
                view.statusComidaIcon.setImageResource(SUCCESS_IMG)
                view.tag = SUCCESS_IMG
            }
        }
    }

    override fun getItemCount(): Int = itemsDieta.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
