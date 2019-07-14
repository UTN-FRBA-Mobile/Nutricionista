package com.utn.nutricionista.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.google.android.material.appbar.AppBarLayout
import com.utn.nutricionista.*
import com.utn.nutricionista.Messages.MessagesActivity
import com.utn.nutricionista.adapters.HomeExpandibleListAdapter
import com.utn.nutricionista.dummy.OnSwipeTouchListener
import com.utn.nutricionista.fragments.HomeDietFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

import com.utn.nutricionista.models.MomentoComida
import kotlinx.android.synthetic.main.activity_home.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HomeActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null
    private var appBarLayout: AppBarLayout? = null

    private val dateFormat = SimpleDateFormat("yyyy/MM/dd", /*Locale.getDefault()*/Locale.ENGLISH)
    private var compactCalendarView: CompactCalendarView? = null
    private var isExpanded = false

    override fun onResume() {
        super.onResume()
        this.progressBarHome.visibility = View.VISIBLE
        init()
    }

    override fun onPause() {
        super.onPause()
        this.progressBarHome.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                //aplicoLoader()
                val formatted = dateFormat.format(dateClicked)
                setSubtitle(formatted)
                //getDietaByDate(formatted)
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

        val fragment = findViewById<View>(R.id.home_recycler_view )
        fragment.setOnTouchListener(OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                // Whatever
            }
        });

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

    }

    fun setPreviousSelectedDate() {
        val date =getSelectedDate()
        val prevDay: LocalDate = date.plusDays(-1)
        val localDateStr: String = formatStringLocalDate("yyyy/MM/dd", prevDay)
        setCurrentDate(Date(localDateStr))

    }

    fun getSelectedDateStr(): String {
            val date = getSelectedDate()
            val localDateStr: String = formatStringLocalDate("yyyy/MM/dd", date)
            return localDateStr
    }

    fun aplicoLoader(activo: Boolean = true){
        if(activo){
            this.progressBarHome.visibility = View.VISIBLE
        }else{
            this.progressBarHome.visibility = View.GONE
        }
    }

    fun formatStringLocalDate(formato: String, localDate: LocalDate): String{
        val formatter = DateTimeFormatter.ofPattern(formato)
        val formatted = localDate.format(formatter)
        return formatted
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
}

