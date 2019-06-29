package com.utn.nutricionista.models

import android.os.Parcel
import android.os.Parcelable

class Comida (

    var realizada : Boolean,
    var nombreComida : String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (realizada) 1 else 0)
        parcel.writeString(nombreComida)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comida> {
        override fun createFromParcel(parcel: Parcel): Comida {
            return Comida(parcel)
        }

        override fun newArray(size: Int): Array<Comida?> {
            return arrayOfNulls(size)
        }
    }
}