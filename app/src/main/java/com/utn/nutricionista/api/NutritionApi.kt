package com.utn.nutricionista.api

import com.utn.nutricionista.models.Weight
import java.time.LocalDate

class NutritionApi {
    private val data = mutableListOf<Weight>()

    constructor() {
        data.add(Weight(1, 1, 85.4, LocalDate.of(2018,9,25)))
        data.add(Weight(2, 1, 82.1, LocalDate.of(2018,10,25)))
        data.add(Weight(8, 1, 80.3, LocalDate.of(2018,11,25)))
        data.add(Weight(9, 1, 78.0, LocalDate.of(2019,1,15)))
        data.add(Weight(7, 1, 78.5, LocalDate.of(2019,1,25)))
        data.add(Weight(3, 1, 75.4, LocalDate.of(2019,2,25)))
        data.add(Weight(4, 1, 77.6, LocalDate.of(2019,4,25)))
    }

    fun getWeights(): MutableList<Weight> {
        return data
    }
}