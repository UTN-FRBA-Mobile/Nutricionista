package com.utn.nutricionista.adapters

import android.app.AlertDialog
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utn.nutricionista.ApiClient


import com.utn.nutricionista.fragments.DetalleComidaFragment.OnListFragmentInteractionListener
import com.utn.nutricionista.R
import com.utn.nutricionista.activities.DetalleComidaActivity
import com.utn.nutricionista.dummy.DummyContent.DummyItem
import com.utn.nutricionista.models.Comida
import com.utn.nutricionista.models.Diet
import com.utn.nutricionista.models.MomentoComida

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
    lateinit var momento: MomentoComida
    lateinit var dietaConcreta: Diet

    fun setData(itemsDietaLst : List<Comida>, tipoDieta: Int, dietaConcreta: Diet, momento: MomentoComida) {
        itemsDieta = itemsDietaLst
        this.tipoDieta = tipoDieta
        this.dietaConcreta = dietaConcreta
        this.momento = momento
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
                checkEstadoComida(it, position)
            }
        }else{
            holder.itemView.setOnLongClickListener { view ->
                AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Eliminar registro")
                    .setMessage("¿Desea eliminar el registro?")
                    .setPositiveButton(android.R.string.yes) { dialog, _ ->
                        (view.context as DetalleComidaActivity).deleteComida(itemsDieta[position])
                        dialog.dismiss()
                    }
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
                return@setOnLongClickListener true
            }
        }

    }

    fun checkEstadoComida(view: View, position: Int){
        when(view.tag){
            SUCCESS_IMG -> {
                view.statusComidaIcon.setImageResource(CROSS_IMG)
                view.tag = CROSS_IMG
                dietaConcreta.updateRealizado(momento.nombre, itemsDieta[position].nombreComida, false)

            }
            CROSS_IMG -> {
                view.statusComidaIcon.setImageResource(SUCCESS_IMG)
                view.tag = SUCCESS_IMG
                dietaConcreta.updateRealizado(momento.nombre, itemsDieta[position].nombreComida, true)
            }
        }
        ApiClient.putDieta(dietaConcreta).addOnSuccessListener {
            val postedDieta = it
            Log.d("SUCCESS", "Saved Id:${postedDieta.id} with fecha ${postedDieta.fecha}, Action: Updated food state of ${itemsDieta[position].nombreComida} from ${momento.nombre}")
        }.addOnFailureListener { e ->
            Log.d("ERROR", "Insert failed with error ${e.message}}")
        }
    }

    override fun getItemCount(): Int = itemsDieta.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}