package com.utn.nutricionista.models

import android.os.Parcel
import android.os.Parcelable

data class MomentoComida (

    var nombre : String,
    var foto : String,
    var predefinida : MutableList<Comida>,
    var extras : MutableList<Comida>

) : Parcelable {
    @Suppress("UNCHECKED_CAST")
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readArrayList(Comida::class.java.classLoader) as MutableList<Comida>,
        parcel.readArrayList(Comida::class.java.classLoader) as MutableList<Comida>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(foto)
        parcel.writeList(predefinida)
        parcel.writeList(extras)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MomentoComida> {
        override fun createFromParcel(parcel: Parcel): MomentoComida {
            return MomentoComida(parcel)
        }

        override fun newArray(size: Int): Array<MomentoComida?> {
            return arrayOfNulls(size)
        }
    }
}