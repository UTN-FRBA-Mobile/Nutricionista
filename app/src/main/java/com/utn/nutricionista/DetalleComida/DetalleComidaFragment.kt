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

    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var detalleComidaAdapter: DetalleComidaAdapter


    override fun onStart() {
        super.onStart()

        //Datos Mockeados --> obtenidos desde la BD
        val dietaPreDefArr = ArrayList<String>()
        dietaPreDefArr.add("Medialunas")
        dietaPreDefArr.add("Tazas de Cafe")
        dietaPreDefArr.add("Te")
        dietaPreDefArr.add("Tostadas")
        dietaPreDefArr.add("Galletitas dulces")

        val dietaRealArr = ArrayList<DetalleComida>()
        dietaRealArr.add(DetalleComida("Tostadas", 2))
        dietaRealArr.add(DetalleComida("Medialunas", 2))
        dietaRealArr.add(DetalleComida("Tazas de Cafe", 1))
        dietaRealArr.add(DetalleComida("Jugo de Naranja", 1))

        dietaRealArr.map{item -> if(item.detalle in dietaPreDefArr) item.enPreDef = true;}

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
        fun newInstance() = DetalleComidaFragment()
    }

}
