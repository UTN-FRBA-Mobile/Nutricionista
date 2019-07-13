package com.utn.nutricionista.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
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

    lateinit private var expandableListView: ExpandableListView
    lateinit private var expandableListViewAdapter: HomeExpandibleListAdapter
    var latestExpandedPosition: Int = -1

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home_diet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            var selectedDate = (activity as HomeActivity).getSelectedDate()
            getDietaByDate(selectedDate)
        }
    }

    private fun getDietaByDate(date: String) {

        ApiClient.getDietsByDate(date).addOnSuccessListener { dietas ->
            val itemNameList  =
                dietas.map{d ->
                    d.momentos?.map { m ->
                        m.nombre.capitalize()
                    }
                }

            expandableListView = view!!.findViewById(R.id.home_expandable_list_view)
            if(itemNameList.size > 0) {

                expandableListViewAdapter = HomeExpandibleListAdapter(
                    this.context!!,
                    dietas[0],
                    dietas[0].momentos as ArrayList<MomentoComida>,
                    itemNameList[0]
                )

                expandableListView.setAdapter(expandableListViewAdapter)
                expandableListView.setOnGroupExpandListener {
                    object : ExpandableListView.OnGroupExpandListener {
                        override fun onGroupExpand(groupPosition: Int) {

                            if (latestExpandedPosition != -1 && groupPosition != latestExpandedPosition) {
                                expandableListView.collapseGroup(latestExpandedPosition)
                            }
                            latestExpandedPosition = groupPosition
                        }
                    }
                }
                //(activity as HomeActivity).aplicoLoader(false)
            }else{
                Toast.makeText(this.context!!, "No se encontraron dietas para la fecha seleccionada.", Toast.LENGTH_LONG).show()
//                this.context!!progressBarHome.visibility = View.GONE
            }

        }.addOnFailureListener { e ->
            Log.d("FAILURE", "GASP! SOMETHING WENT WRONG: ${e.message}")
        }

    }
}
