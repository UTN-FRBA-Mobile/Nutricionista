package com.utn.nutricionista

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.snackbar.Snackbar
import com.utn.nutricionista.api.NutritionApi
import com.utn.nutricionista.models.WeightData
import kotlinx.android.synthetic.main.activity_weight.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utn.nutricionista.adapters.WeightDataAdapter


class WeightActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)
        setSupportActionBar(toolbar)

        val weightRecords = NutritionApi().getWeights()
        loadChart(weightRecords)
        loadTable(weightRecords)

        fab.setOnClickListener { view -> addWeightRecord(view) }
    }

    @SuppressLint("OnClick")
    private fun addWeightRecord(view: View) {
        Snackbar.make(view, "TODO: Add dialog", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun loadTable(weightRecords : MutableList<WeightData>) {
        viewManager = LinearLayoutManager(this)
        var sortedData = weightRecords.sortedBy { x -> x.date }.asReversed()
        viewAdapter = WeightDataAdapter(sortedData.toMutableList())
        viewAdapter.notifyDataSetChanged()

        recyclerView = findViewById<RecyclerView>(R.id.weight_table).apply {
            setHasFixedSize(false   )
            layoutManager = viewManager
            addItemDecoration(ItemOffsetDecoration(20))
            adapter = viewAdapter
        }
    }

    private fun loadChart(weightRecords : MutableList<WeightData>){
        val chart = findViewById<LineChart>(R.id.weight_chart)

        val entries = ArrayList<Entry>()
        var dayStart = 0
        var dateLabels = mutableMapOf<Long,String>()

        for (weightRecord in weightRecords) {
            var day = weightRecord.date.toEpochDay()
            if (dayStart == 0)
                dayStart = day.toInt()

            dateLabels[day] = weightRecord.date.toString()
            var dayAdjusted = day - dayStart
            entries.add(Entry((dayAdjusted).toFloat(), weightRecord.weight.toFloat()))
        }

        val dataSet = LineDataSet(entries,"Peso")
        dataSet.isHighlightEnabled = true
        dataSet.setDrawHighlightIndicators(true)
        dataSet.lineWidth = 3f

        val lineData = LineData(dataSet)
        
        val xAxis = chart.xAxis
        chart.data = lineData
        xAxis.valueFormatter = GridLabelDateFormatter(dayStart)
        xAxis.position = XAxis.XAxisPosition.TOP_INSIDE
        xAxis.textSize = 10f
        xAxis.textColor = Color.BLUE
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.textColor = Color.rgb(255, 192, 56)
        xAxis.setCenterAxisLabels(false)
        xAxis.granularity = 30f
        chart.setTouchEnabled(false)
        chart.isHighlightPerTapEnabled = true
        chart.legend.isEnabled = false
        chart.setNoDataText("Sin datos disponibles.")
        chart.setDrawGridBackground(true)
        chart.setDrawBorders(true)
        chart.invalidate()
    }

    class GridLabelDateFormatter(private val dayStart : Int) : ValueFormatter() {
        private val format = SimpleDateFormat("MMM yyy")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            var days = dayStart + value
            var valueDate = Date(TimeUnit.DAYS.toMillis(days.toLong()))
            return format.format(valueDate)
        }
    }
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


