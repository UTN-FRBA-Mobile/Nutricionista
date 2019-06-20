package com.utn.nutricionista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListView
import androidx.appcompat.widget.Toolbar

class HomeActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null
    lateinit private var expandableListView: ExpandableListView
    lateinit private var expandableListViewAdapter: HomeExpandibleListAdapter
    var dietaPreDefArr = HashMap<String,List<String>>()
    var latestExpandedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()


        toolbar = findViewById(R.id.toolbarHome)
        toolbar?.setTitle(R.string.dieta_home)
        setSupportActionBar(toolbar)



        expandableListView.setAdapter(expandableListViewAdapter)
        expandableListView.setOnGroupExpandListener { object : ExpandableListView.OnGroupExpandListener {
            override fun onGroupExpand(groupPosition: Int) {

                if(latestExpandedPosition != -1 && groupPosition != latestExpandedPosition){
                    expandableListView.collapseGroup(latestExpandedPosition)
                }
                latestExpandedPosition = groupPosition
            }
        } }


    }

    fun buttonPressed(view: View) {
        val intent = Intent(this, DietActivity::class.java)
        startActivity(intent)
    }

    private fun init(){


        dietaPreDefArr["Desayuno"] = listOf(
             "Medialunas",
             "Tazas de Cafe",
             "Tostadas",
             "Galletitas dulces",
             "Te"
        )
        dietaPreDefArr["Almuerzo"] = listOf(
             "Ensalada mixta",
             "2 rodajas de pan",
             "Currazco de cerdo"
        )
        dietaPreDefArr["Merienda"] = listOf(
            "1 o 2 Frutas",
            "Yogurt con cereales",
            "Alfajor",
            "Cafe",
           "Te"
        )
        dietaPreDefArr["Cena"] = listOf(
             "Jugo de naraja",
             "Pollo Grille",
             "Pure de batata"
        )


        val itemNameList: List<String> = ArrayList(dietaPreDefArr.keys) as List<String>

        expandableListView = findViewById(R.id.home_expandable_list_view)
        expandableListViewAdapter = HomeExpandibleListAdapter(this, dietaPreDefArr, itemNameList)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.manu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.chat ->{
                val intent = Intent(this, MyProgressActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }




}
