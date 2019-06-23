package com.utn.nutricionista

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.utn.nutricionista.detalleComida.DetalleComidaActivity
import com.utn.nutricionista.models.MomentoComida

class HomeExpandibleListAdapter(var context: Context, var listOfMomentos:ArrayList<MomentoComida>, var listOfTitulosComida: List<String>?): BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return listOfTitulosComida!![groupPosition]
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

        val itemText = convertView_Aux!!.findViewById<TextView>(R.id.home_list_tb)
        itemText?.setText(nombre)

        val cameraIcon = convertView_Aux.findViewById<ImageView>(R.id.home_list_camera)
        cameraIcon.setOnClickListener{
            val intent = Intent(context, DetalleComidaActivity::class.java)
            intent.putExtra("dietaSeleccionada", listOfMomentos[groupPosition])
            context.startActivity(intent)
        }

        return convertView_Aux
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listOfMomentos[groupPosition].predefinida.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String? {
        return listOfMomentos[groupPosition].predefinida.get(childPosition).nombreComida.capitalize()
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

        val itemText = convertView_Aux!!.findViewById<TextView>(R.id.detalleItemTxt)
        val animation: Animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)

        itemText?.text = getChild(groupPosition, childPosition)!!
        convertView_Aux.startAnimation(animation)

        return convertView_Aux
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return 0
    }

    override fun getGroupCount(): Int {
        return listOfTitulosComida!!.size
    }


}