package com.utn.nutricionista.models
import java.time.LocalDate

class Weight(var id:  String,
             var uid : String? = null,
             var peso: Double,
             var fecha : String)
{
    fun date(): LocalDate {
        return LocalDate.parse(this.fecha)
    }
}