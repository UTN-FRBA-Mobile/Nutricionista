package com.utn.nutricionista.api

import com.utn.nutricionista.models.Weight
import java.time.LocalDate

class NutritionApi {
    private val data = mutableListOf<Weight>()

    constructor() {
        data.add(Weight("id1","id1",  85.4, "2018-09-25"))
        data.add(Weight("id2","id1",  82.1, "2018-10-25"))
        data.add(Weight("id8","id1",  80.3, "2018-11-25"))
        data.add(Weight("id9","id1",  78.0, "2019-01-15"))
        data.add(Weight("id7","id1",  78.5, "2019-01-25"))
        data.add(Weight("id3","id1",  75.4, "2019-02-25"))
        data.add(Weight("id4","id1",  77.6, "2019-04-25"))
    }

    fun getWeights(): MutableList<Weight> {
        return data
    }
}