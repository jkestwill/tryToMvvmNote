package com.example.rxjavapizdec.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(

    @ColumnInfo(name="title")
    var title: String,
    @ColumnInfo(name="description")
    var description: String,
    @ColumnInfo(name="thumbnail",typeAffinity = ColumnInfo.BLOB)
    var thumbnail:ByteArray,
    @Embedded
    var styles: Styles,
    @ColumnInfo(name = "date")
    val date: String
){

    @PrimaryKey(autoGenerate =true)
    var uid: Long=0


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (!thumbnail.contentEquals(other.thumbnail)) return false

        return true
    }

    override fun hashCode(): Int {
        return thumbnail.contentHashCode()
    }
}

data class Styles(
    @ColumnInfo(name="font_size")
    var fontSize:Int,
    @ColumnInfo(name="bold")
    var bold:Boolean
)
