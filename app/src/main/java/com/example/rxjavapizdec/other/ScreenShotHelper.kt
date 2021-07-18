package com.example.rxjavapizdec.other

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

import android.view.View
import java.io.ByteArrayOutputStream

fun screenShot(view: View): Bitmap {
    val bitmap= Bitmap.createBitmap(480,640, Bitmap.Config.ARGB_8888)
    val canvas= Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun screenShotToByteArray(bitmap: Bitmap):ByteArray{
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
    val byteArray= stream.toByteArray()
    stream.close()
    return byteArray
}

fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
    val options = BitmapFactory.Options()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)

}


