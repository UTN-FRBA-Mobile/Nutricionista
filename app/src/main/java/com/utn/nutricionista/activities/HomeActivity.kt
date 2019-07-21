package com.utn.nutricionista.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.google.android.material.appbar.AppBarLayout
import com.utn.nutricionista.*
import com.utn.nutricionista.Messages.MessagesActivity
import com.utn.nutricionista.adapters.HomeExpandibleListAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

import com.utn.nutricionista.models.MomentoComida
import kotlinx.android.synthetic.main.activity_home.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HomeActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    private val SWIPE_THRESHOLD = 100
    private val SWIPE_VELOCITY_THRESHOLD = 100

    private var gestureDetector: GestureDetector? = null

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        var result = false
        try {
            val diffY = (e2?.y ?: 0.0.toFloat()) - (e1?.y ?: 0.0.toFloat())
            val diffX = (e2?.x ?: 0.0.toFloat()) - (e1?.x ?: 0.0.toFloat())
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight()
                    } else {
                        onSwipeLeft()
                    }
                    result = true
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return result
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    fun onSwipeRight() {
        Toast.makeText(this, "swipe right", Toast.LENGTH_LONG).show()
        setPreviousSelectedDate()
    }

    fun onSwipeLeft() {
        Toast.makeText(this, "swipe left", Toast.LENGTH_LONG).show()
        setNextSelectedDate()
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    var toolbar: Toolbar? = null
    private var appBarLayout: AppBarLayout? = null

    private val dateFormat = SimpleDateFormat("d MMMM yyyy", /*Locale.getDefault()*/Locale.ENGLISH)
    private var compactCalendarView: CompactCalendarView? = null
    private var isExpanded = false









    lateinit private var expandableListView: ExpandableListView
    lateinit private var expandableListViewAdapter: HomeExpandibleListAdapter
    var dietaPreDefArr = HashMap<String,List<String>>()
    var latestExpandedPosition: Int = -1

    override fun onResume() {
        super.onResume()
        this.progressBarHome.visibility = View.VISIBLE
        init()
    }

    override fun onPause() {
        super.onPause()
        this.expandableListView.visibility = View.GONE
        this.progressBarHome.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestureDetector = GestureDetector(this, this)
        if (SessionManager.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        setContentView(R.layout.activity_home)

        toolbar = findViewById(R.id.toolbarHome)
        title = "Dieta"
        setSupportActionBar(toolbar)

        appBarLayout = findViewById(R.id.app_bar_layout)
        // Set up the CompactCalendarView
        compactCalendarView = findViewById(R.id.compactcalendar_view)
        //compactCalendarView!!.setCalendarBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        //compactCalendarView!!.setCurrentSelectedDayBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight))
        // Force English
        compactCalendarView!!.setLocale(TimeZone.getDefault(), /*Locale.getDefault()*/Locale.ENGLISH)
        compactCalendarView!!.setShouldDrawDaysHeader(true)

        compactCalendarView!!.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                getNewDate(dateClicked)
                isExpanded = false
                appBarLayout!!.setExpanded(isExpanded, true)
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                setSubtitle(dateFormat.format(firstDayOfNewMonth))
            }
        })

        // Set current date to today
        setCurrentDate(Date())

        val arrow = findViewById<ImageView>(R.id.date_picker_arrow)

        val datePickerButton = findViewById<RelativeLayout>(R.id.date_picker_button)

        datePickerButton.setOnClickListener { v ->
            val rotation = (if (isExpanded) 0 else 180).toFloat()
            ViewCompat.animate(arrow).rotation(rotation).start()

            isExpanded = !isExpanded
            appBarLayout!!.setExpanded(isExpanded, true)
        }
    init()

    }

    private fun getNewDate(dateClicked: Date) {
        aplicoLoader()
        val formatted = dateFormat.format(dateClicked)
        setSubtitle(formatted)
        getDietaByDate(formatted)
    }

    fun buttonPressed(view: View) {
        val intent = Intent(this, DietActivity::class.java)
        startActivity(intent)
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

    override fun setTitle(title: CharSequence) {
        val tvTitle = findViewById<TextView>(R.id.title)

        if (tvTitle != null) {
            tvTitle.text = title
        }
    }


    private fun init(){

        val currentDate = LocalDateTime.now().toLocalDate()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val formatted = currentDate.format(formatter)
        getDietaByDate(formatted)

    }

    private fun init(){

    private fun aplicoLoader(activo: Boolean = true){
        if(activo){
            this.expandableListView.visibility = View.GONE
            this.progressBarHome.visibility = View.VISIBLE
        }else{
            this.expandableListView.visibility = View.VISIBLE
            this.progressBarHome.visibility = View.GONE
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

            expandableListView = findViewById(R.id.home_expandable_list_view)
            if(itemNameList.size > 0) {

                expandableListViewAdapter = HomeExpandibleListAdapter(
                    this,
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
                aplicoLoader(false)
            }else{
                Toast.makeText(this, "No se encontraron dietas para la fecha seleccionada.", Toast.LENGTH_LONG).show()
                this.progressBarHome.visibility = View.GONE
            }

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
            R.id.peso ->{
                val intent = Intent(this, WeightActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun getSelectedDate() : LocalDate {
        val datePickerTextView = findViewById<TextView>(R.id.date_picker_text_view)
        val string = datePickerTextView.text.toString()
        val date = LocalDate.parse(string, DateTimeFormatter.ofPattern("yyyy/MM/dd"))

        return date
    }

    fun setNextSelectedDate() {
        val date =getSelectedDate()
        val nextDay: LocalDate = date.plusDays(1)
        val localDateStr: String = formatStringLocalDate("yyyy/MM/dd", nextDay)
        setCurrentDate(Date(localDateStr))
        getNewDate(Date(localDateStr))
    }

    fun setPreviousSelectedDate() {
        val date =getSelectedDate()
        val prevDay: LocalDate = date.plusDays(-1)
        val localDateStr: String = formatStringLocalDate("yyyy/MM/dd", prevDay)
        setCurrentDate(Date(localDateStr))
        getNewDate(Date(localDateStr))
    }

    fun getSelectedDateStr(): String {
        val date = getSelectedDate()
        val localDateStr: String = formatStringLocalDate("yyyy/MM/dd", date)
        return localDateStr
    }

    fun formatStringLocalDate(formato: String, localDate: LocalDate): String{
        val formatter = DateTimeFormatter.ofPattern(formato)
        val formatted = localDate.format(formatter)
        return formatted
    }

}
