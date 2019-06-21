package com.utn.nutricionista.models

data class MomentoComida (

    var nombre : String,
    var foto : String,
    var predefinida : List<Comida>,
    var extras : List<Comida>

)