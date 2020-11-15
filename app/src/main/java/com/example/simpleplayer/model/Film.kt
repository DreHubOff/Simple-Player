package com.example.simpleplayer.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable


data class Film @JvmOverloads constructor(
    val title: String,
    val filmURL: Uri,
    val rating: Double,
    val posterUri: Uri,
    var id: Int = 0,
    val filmFileLink: Uri? = null,
    val offlineViewing: Boolean = false,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        title = parcel.readString() ?: "",
        filmURL = parcel.readParcelable(Uri::class.java.classLoader) ?: Uri.parse(""),
        rating = parcel.readDouble(),
        posterUri = parcel.readParcelable(Uri::class.java.classLoader) ?: Uri.parse(""),
        id = parcel.readInt(),
        filmFileLink = parcel.readParcelable(Uri::class.java.classLoader),
        offlineViewing = parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeParcelable(filmURL, flags)
        parcel.writeDouble(rating)
        parcel.writeParcelable(posterUri, flags)
        parcel.writeInt(id)
        parcel.writeParcelable(filmFileLink, flags)
        parcel.writeByte(if (offlineViewing) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Film> {
        override fun createFromParcel(parcel: Parcel): Film {
            return Film(parcel)
        }

        override fun newArray(size: Int): Array<Film?> {
            return arrayOfNulls(size)
        }
    }
}