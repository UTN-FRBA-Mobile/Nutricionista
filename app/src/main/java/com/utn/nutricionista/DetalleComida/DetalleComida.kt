package com.utn.nutricionista.DetalleComida
val DIETA_PREDEF = 1
val FUERA_DIETA_PREDEF = 2
val DIETA_PREDEF_NO_CONSUMIDO = 3
data class DetalleComida(var detalle : String,
                         var cantidad: Int,
                         var enPreDef: Boolean = false,
                         var tipoItem: Int = DIETA_PREDEF_NO_CONSUMIDO){

}