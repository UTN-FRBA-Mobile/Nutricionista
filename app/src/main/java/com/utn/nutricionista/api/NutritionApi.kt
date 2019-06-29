package com.utn.nutricionista.api

import com.utn.nutricionista.models.WeightData
import java.time.LocalDate

class NutritionApi {
    private val data = mutableListOf<WeightData>()

    constructor() {
        data.add(WeightData(1, 1, 85.4, LocalDate.of(2018,9,25)))
        data.add(WeightData(2, 1, 82.1, LocalDate.of(2018,10,25)))
        data.add(WeightData(8, 1, 80.3, LocalDate.of(2018,11,25)))
        data.add(WeightData(9, 1, 78.0, LocalDate.of(2019,1,15)))
        data.add(WeightData(7, 1, 78.5, LocalDate.of(2019,1,25)))
        data.add(WeightData(3, 1, 75.4, LocalDate.of(2019,2,25)))
        data.add(WeightData(4, 1, 77.6, LocalDate.of(2019,4,25)))
    }

    fun getWeights(): MutableList<WeightData> {
        return data
    }
}