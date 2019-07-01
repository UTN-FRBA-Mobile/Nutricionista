package com.utn.nutricionista.models

data class Diet(val uid: String? = null,
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

    fun updateFotoComida(momentoSelected: String, path: String){
        momentos!!.forEach {
                elem -> if(elem.nombre == momentoSelected){
                                elem.foto = path
                            }
        }
    }


    fun getMomento(nombreMomento: String): MomentoComida{

        return momentos!!.filter{
            it.nombre == nombreMomento
        }.first()

    }

    fun addNewComida(momentoSelected: String, newComida: Comida){
        momentos!!.forEach {
                elem -> if(elem.nombre == momentoSelected){
            elem.extras.add(newComida)
        }
        }
    }

    fun deleteComida(comida: Comida, momentoSelected: String){
        momentos!!.forEach {
                elem -> if(elem.nombre == momentoSelected){
                        elem.extras.remove(comida)
            }
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