package com.utn.nutricionista.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import com.utn.nutricionista.ApiClient
import com.utn.nutricionista.R
import com.utn.nutricionista.adapters.HomeExpandibleListAdapter
import com.utn.nutricionista.models.MomentoComida
import com.utn.nutricionista.activities.HomeActivity

private const val ARG_OBJECT = "object"

// Instances of this class are fragments representing a single
// object in our collection.
class HomeDietFragment : Fragment() {

    private lateinit var expandableListView: ExpandableListView
    private lateinit var expandableListViewAdapter: HomeExpandibleListAdapter
    var latestExpandedPosition: Int = -1

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home_diet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        expandableListView = view.findViewById(R.id.home_expandable_list_view)
        this.expandableListView.visibility = View.GONE

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            (activity as HomeActivity).aplicoLoader(true)
            (activity as HomeActivity).setNextSelectedDate()
            val selectedDate: String = (activity as HomeActivity).getSelectedDateStr()
            getDietaByDate(selectedDate)
        }

        val dateSelect = (activity as HomeActivity).findViewById<TextView>(R.id.date_picker_text_view)
        dateSelect.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                getDietaByDate(text.toString())
            }

            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        })
    }

    private fun getDietaByDate(date: String) {
        this.expandableListView.visibility = View.GONE
        ApiClient.getDietsByDate(date).addOnSuccessListener { dietas ->
            val itemNameList  =
                dietas.map{d ->
                    d.momentos?.map { m ->
                        m.nombre.capitalize()
                    }
                }

            //expandableListView = view!!.findViewById(R.id.home_expandable_list_view)
            if(itemNameList.isNotEmpty()) {

                expandableListViewAdapter = HomeExpandibleListAdapter(
                    this.context!!,
                    dietas[0],
                    dietas[0].momentos as ArrayList<MomentoComida>,
                    itemNameList[0]
                )

                expandableListView.setAdapter(expandableListViewAdapter)
                expandableListView.setOnGroupExpandListener {
                    ExpandableListView.OnGroupExpandListener { groupPosition ->
                        if (latestExpandedPosition != -1 && groupPosition != latestExpandedPosition) {
                            expandableListView.collapseGroup(latestExpandedPosition)
                        }
                        latestExpandedPosition = groupPosition
                    }
                }
                this.expandableListView.visibility = View.VISIBLE
                (activity as HomeActivity).aplicoLoader(false)
            }else{
                Toast.makeText(this.context!!, "No se encontraron dietas para la fecha seleccionada.", Toast.LENGTH_LONG).show()
                (activity as HomeActivity).aplicoLoader(false)
            }

        }.addOnFailureListener { e ->
            Log.d("FAILURE", "GASP! SOMETHING WENT WRONG: ${e.message}")
        }
    }
}
