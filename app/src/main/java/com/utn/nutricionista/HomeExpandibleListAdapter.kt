package com.utn.nutricionista

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.utn.nutricionista.DetalleComida.DetalleComida

class HomeExpandibleListAdapter(var context: Context, var listOfDetalleComida:HashMap<String,List<String>>, var listOfTitulosComida: List<String>): BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return listOfTitulosComida[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val nombre:String = getGroup(groupPosition) as String
        var convertView_Aux = convertView

        if(convertView == null){
            val layoutInflater:LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            convertView_Aux = layoutInflater.inflate(R.layout.home_list_group, parent, false)
        }

        val itemText = convertView_Aux?.findViewById<TextView>(R.id.home_list_tb)
        itemText?.setText(nombre)

        return convertView_Aux!!
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listOfDetalleComida[listOfTitulosComida[groupPosition]]?.size!!
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String? {
        return listOfDetalleComida[listOfTitulosComida[groupPosition]]?.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return 0
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        var convertView_Aux = convertView
        if(convertView == null){
            val layoutInflater:LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView_Aux = layoutInflater.inflate(R.layout.fragment_detalle_comida, parent, false)
        }

        val itemText = convertView_Aux?.findViewById<TextView>(R.id.detalleItemTxt)

        itemText?.text = getChild(groupPosition, childPosition)!!

        val animation: Animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        convertView?.startAnimation(animation)

        return convertView_Aux!!
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return 0
    }

    override fun getGroupCount(): Int {
        return listOfTitulosComida.size
    }


}