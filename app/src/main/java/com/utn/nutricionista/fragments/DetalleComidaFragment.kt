package com.utn.nutricionista.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utn.nutricionista.R
import com.utn.nutricionista.models.Comida
import com.utn.nutricionista.models.Diet
import com.utn.nutricionista.models.MomentoComida
import androidx.recyclerview.widget.DividerItemDecoration
import com.utn.nutricionista.adapters.DetalleComidaAdapter


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

        val momento: MomentoComida = this.arguments!!.getParcelable("momento")!!
        val tipoDieta : Int = this.arguments!!.getInt("tipoDieta")
        var dietaPreDefArr = momento.predefinida
        var dietaRealArr = momento.extras
        when(tipoDieta){
            DIETA_PREDEF -> {
                dietaRealArr = dietaPreDefArr.onEach { item -> if(item.nombreComida in dietaRealArr.map { e -> e.nombreComida }) item.realizada = true }

            }
            FUERA_DIETA_PREDEF -> {
                dietaRealArr = dietaRealArr.filter{ item -> item.nombreComida !in dietaPreDefArr.map { e->e.nombreComida } } as MutableList<Comida>
            }
            else ->{
            }
        }

        detalleComidaAdapter.setData(dietaRealArr, tipoDieta)
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
            view.addItemDecoration(DividerItemDecoration(view.context,1))
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
        fun newDietaInstance(momento : MomentoComida, tipoDieta : Int): DetalleComidaFragment {
            val newFragment = DetalleComidaFragment()
            val args = Bundle()
            args.putParcelable("momento", momento)
            args.putInt("tipoDieta", tipoDieta)
            newFragment.arguments = args
            return newFragment
        }
    }

}
