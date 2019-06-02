package com.utn.nutricionista.DetalleComida

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utn.nutricionista.R

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [DetalleComidaFragment.OnListFragmentInteractionListener] interface.
 */
class DetalleComidaFragment : Fragment() {

    val DIETA_PREDEF = 1
    val FUERA_DIETA_PREDEF = 2

    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var detalleComidaAdapter: DetalleComidaAdapter


    override fun onStart() {
        super.onStart()

        //Datos Mockeados --> obtenidos desde la BD
        val dietaPreDefArr:  List<DetalleComida> = arrayListOf(
            DetalleComida("Medialunas", 2),
            DetalleComida("Tazas de Cafe", 1),
            DetalleComida("Tostadas", 2),
            DetalleComida("Galletitas dulces", 3),
            DetalleComida("Te", 2)
        )

        //Datos Mockeados --> obtenidos desde la BD
        var dietaRealArr: List<DetalleComida> = arrayListOf(
            DetalleComida("Tostadas", 2),
            DetalleComida("Medialunas", 2),
            DetalleComida("Tazas de Cafe", 1),
            DetalleComida("Jugo de Naranja", 1)
        )


        val tipoDieta: Int = this.arguments!!.getInt("tipoDieta")
        when(tipoDieta){
            DIETA_PREDEF -> {
                dietaRealArr = dietaPreDefArr.onEach { item -> if(item.detalle in dietaRealArr.map { e -> e.detalle }) item.tipoItem = DIETA_PREDEF }

            }
            FUERA_DIETA_PREDEF -> {
                dietaRealArr = dietaRealArr.filter{ item -> item.detalle !in dietaPreDefArr.map { e->e.detalle } }.onEach { e -> e.tipoItem=FUERA_DIETA_PREDEF }
            }
            else ->{
            }
        }

        detalleComidaAdapter.setData(dietaRealArr)
        detalleComidaAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detalle_comida_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detalleComidaAdapter = DetalleComidaAdapter(listener)
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = detalleComidaAdapter
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
       fun showFragment(fragment: Fragment)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param title Title.
         * @return A new instance of fragment MainFragment.
         */
        @JvmStatic
        fun newExtraDietaInstance(): DetalleComidaFragment {
            val newFragment = DetalleComidaFragment()
            val args = Bundle()
            args.putInt("tipoDieta", 2)
            newFragment.arguments = args
            return newFragment
        }

        @JvmStatic
        fun newPreDefDietaInstance(): DetalleComidaFragment {
            val newFragment = DetalleComidaFragment()
            val args = Bundle()
            args.putInt("tipoDieta", 1)
            newFragment.arguments = args
            return newFragment
        }
    }

}
