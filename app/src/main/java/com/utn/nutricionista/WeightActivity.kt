package com.utn.nutricionista

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.utn.nutricionista.models.Weight
import kotlinx.android.synthetic.main.activity_weight.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utn.nutricionista.adapters.WeightDataAdapter
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.formatter.IFillFormatter
import kotlinx.android.synthetic.main.fragment_weight.*
import com.utn.nutricionista.R

class WeightActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var pesos : MutableList<Weight>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)
        setSupportActionBar(toolbar)
        title = "Mi Peso"
        fab.setOnClickListener { view -> openAddWeightRecord(view) }

        refreshWeightData()
    }

    private fun refreshWeightData() {
        val progressBar: ProgressBar = this.progressBar
        progressBar.visibility = View.VISIBLE
        ApiClient.getWeights().addOnSuccessListener {
            Log.d("SUCCESS", "SWEET, SWEET SUCCESS!")
            pesos = it.toMutableList()
            progressBar.visibility = View.GONE
            loadChart(pesos)
            loadTable(pesos)
        }.addOnFailureListener { e ->
            Log.d("FAILURE", "GASP! SOMETHING WENT WRONG: ${e.message}")
        }
    }

    private fun openAddWeightRecord(view: View) {
        InputWeightDialogFragment().show(this.supportFragmentManager,"inputWeight")
    }

    //region Set Up Data Display

    private fun loadTable(weightRecords : MutableList<Weight>) {
        viewManager = LinearLayoutManager(this)
        var sortedData = weightRecords.sortedBy { x -> x.date() }.asReversed()
        viewAdapter = WeightDataAdapter(sortedData.toMutableList(), this)
        viewAdapter.notifyDataSetChanged()

        recyclerView = findViewById<RecyclerView>(R.id.weight_table).apply {
            setHasFixedSize(false   )
            layoutManager = viewManager
            addItemDecoration(ItemOffsetDecoration(20))
            adapter = viewAdapter
        }
    }

    private fun loadChart(weightRecords : MutableList<Weight>){
        val entries = ArrayList<Entry>()
        var dayStart = 0
        var dateLabels = mutableMapOf<Long,String>()
        var sortedData = weightRecords.sortedBy { x -> x.date() }

        for (weightRecord in sortedData) {
            var day = weightRecord.date().toEpochDay()
            if (dayStart == 0)
                dayStart = day.toInt()

            dateLabels[day] = weightRecord.date().toString()
            var dayAdjusted = day - dayStart
            entries.add(Entry((dayAdjusted).toFloat(), weightRecord.peso))
        }

        val chart = configureChart(dayStart)

        //Add Data
        val lineData = configureDataSet(entries, chart!!)
        chart!!.data = lineData

        // refresh the drawing
        chart.invalidate()
    }

    private fun configureDataSet(entries: ArrayList<Entry>, chart: LineChart): LineData {
        val dataSet = LineDataSet(entries, "Peso")
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.cubicIntensity = 0.2f
        dataSet.setDrawFilled(true)
        dataSet.setDrawCircles(false)
        dataSet.lineWidth = 1.8f
        dataSet.circleRadius = 4f
        dataSet.setCircleColor(Color.WHITE)
        dataSet.highLightColor = R.color.chartHighlight
        dataSet.color = R.color.chartColor
        dataSet.fillColor = R.color.chartFillColor
        dataSet.fillAlpha = 100
        dataSet.setDrawHorizontalHighlightIndicator(false)
        dataSet.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.getAxisLeft().getAxisMinimum() }
        dataSet.isHighlightEnabled = true
        dataSet.setDrawHighlightIndicators(true)
        dataSet.lineWidth = 3f
        val lineData = LineData(dataSet)
        lineData.setValueTextSize(9f)
        lineData.setDrawValues(false)
        return lineData
    }

    private fun configureChart(dayStart: Int): LineChart? {
        val chart = findViewById<LineChart>(R.id.weight_chart)
        chart.setViewPortOffsets(0F, 0F, 0F, 0F)
        chart.setBackgroundColor(Color.WHITE)
        // enable touch gestures
        chart.setTouchEnabled(true)
        // enable scaling and dragging
        chart.setDragEnabled(true)
        chart.setScaleEnabled(true)
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)
        chart.setMaxHighlightDistance(300F)

        chart.isHighlightPerTapEnabled = true
        chart.legend.isEnabled = false
        chart.setNoDataText("Sin datos disponibles.")
        chart.setDrawBorders(true)

        val xAxis = chart.xAxis
        xAxis.valueFormatter = GridLabelDateFormatter(dayStart)
        xAxis.position = XAxis.XAxisPosition.TOP_INSIDE
        xAxis.textSize = 10f
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.textColor = R.color.colorPrimary
        xAxis.setCenterAxisLabels(false)
        xAxis.granularity = 30f

        val y = chart.axisLeft
        y.setLabelCount(6, false)
        y.textColor = R.color.chartColor
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(false)
        y.axisLineColor = R.color.white

        chart.getAxisRight().setEnabled(false)
        chart.animateXY(2000, 2000)
        chart.getLegend().setEnabled(false)
        return chart
    }

    class GridLabelDateFormatter(private val dayStart : Int) : ValueFormatter() {
        private val format = SimpleDateFormat("MMM yyy")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            var days = dayStart + value
            var valueDate = Date(TimeUnit.DAYS.toMillis(days.toLong()))
            return format.format(valueDate)
        }
    }

    //endregion

    //region Data Operations

    fun saveNewWeightRecord(weight : Float, date : String) {
        var newRecord = Weight(null,null,weight,date)

        ApiClient.postWeight(newRecord).addOnSuccessListener {
            val postedWeight = it
            refreshWeightData()
            Log.d("SUCCESS", "Saved Id:${postedWeight.id} with peso ${postedWeight.peso}, fecha ${postedWeight.fecha}")
        }.addOnFailureListener { e ->
            Log.d("ERROR", "Insert failed with error ${e.message}}")
        }
    }

    fun deleteWeight(id: String) {
        ApiClient.deleteWeight(id).addOnSuccessListener {
            refreshWeightData()
        }
    }

    //endregion
}

class ItemOffsetDecoration(var offset : Int) : RecyclerView.ItemDecoration() {

    init {
        offset = offset
    }

    override fun getItemOffsets(outRect : Rect, view : View, parent : RecyclerView, state : RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.right = offset;
            outRect.left = offset;
            outRect.top = offset;
            outRect.bottom = offset;
        }
    }
}


