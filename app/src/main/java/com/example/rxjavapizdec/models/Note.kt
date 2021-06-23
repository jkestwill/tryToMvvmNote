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
    @ColumnInfo(name="thumbnail")
    var thumbnail:String,
    @Embedded
    var styles: Styles
){
    @PrimaryKey(autoGenerate =true)
    var uid: Long=0
}

data class Styles(
    @ColumnInfo(name="font_size")
    var fontSize:Int,
    @ColumnInfo(name="bold")
    var bold:Boolean
)
