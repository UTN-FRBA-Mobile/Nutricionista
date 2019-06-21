package com.utn.nutricionista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.utn.nutricionista.models.MomentoComida


class HomeActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null
    private var appBarLayout: AppBarLayout? = null

    private val dateFormat = SimpleDateFormat("d MMMM yyyy", /*Locale.getDefault()*/Locale.ENGLISH)
    private var compactCalendarView: CompactCalendarView? = null
    private var isExpanded = false









    lateinit private var expandableListView: ExpandableListView
    lateinit private var expandableListViewAdapter: HomeExpandibleListAdapter
    var dietaPreDefArr = HashMap<String,List<String>>()
    var latestExpandedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar = findViewById(R.id.toolbarHome)
        setSupportActionBar(toolbar)

        init()

    }


    private fun setCurrentDate(date: Date) {
        setSubtitle(dateFormat.format(date))
        if (compactCalendarView != null) {
            compactCalendarView!!.setCurrentDate(date)
        }
    }

    private fun setSubtitle(subtitle: String) {
        val datePickerTextView = findViewById<TextView>(R.id.date_picker_text_view)

        if (datePickerTextView != null) {
            datePickerTextView.text = subtitle
        }
    }

    fun buttonPressed(view: View) {
        val intent = Intent(this, DietActivity::class.java)
        startActivity(intent)
    }

    private fun init(){


        ApiClient.getDiets().addOnSuccessListener { dietas ->
           val itemNameList  =
           dietas.map{d ->
               d.momentos?.map { m ->
                   m.nombre.capitalize()
               }
           }

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


            expandableListView = findViewById(R.id.home_expandable_list_view)
            expandableListViewAdapter = HomeExpandibleListAdapter(
                this,
                dietas[0].momentos as ArrayList<MomentoComida>,
                itemNameList[0]
            )

            expandableListView.setAdapter(expandableListViewAdapter)
            expandableListView.setOnGroupExpandListener { object : ExpandableListView.OnGroupExpandListener {
                override fun onGroupExpand(groupPosition: Int) {

                    if(latestExpandedPosition != -1 && groupPosition != latestExpandedPosition){
                        expandableListView.collapseGroup(latestExpandedPosition)
                    }
                    latestExpandedPosition = groupPosition
                }
            } }


        }.addOnFailureListener { e ->
            Log.d("FAILURE", "GASP! SOMETHING WENT WRONG: ${e.message}")
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.manu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.chat ->{
                val intent = Intent(this, MessagesActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }




}
