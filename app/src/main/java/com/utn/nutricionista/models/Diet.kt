package com.utn.nutricionista.models

import android.os.Parcel
import android.os.Parcelable

data class Diet(
                val id: String,
                val uid: String? = null,
                val fecha: String,
                var momentos : List<MomentoComida>? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(MomentoComida)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(uid)
        parcel.writeString(fecha)
        parcel.writeTypedList(momentos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Diet> {
        override fun createFromParcel(parcel: Parcel): Diet {
            return Diet(parcel)
        }

        override fun newArray(size: Int): Array<Diet?> {
            return arrayOfNulls(size)
        }
    }

    fun updateRealizado(momentoSelected: String, comidaSelected: String, realizada: Boolean){
        momentos!!.forEach {
            elem -> if(elem.nombre == momentoSelected){
                    elem.predefinida.forEach{
                        if(it.nombreComida == comidaSelected){
                            it.realizada = realizada
                    }
                }
            }
        }
    }

}