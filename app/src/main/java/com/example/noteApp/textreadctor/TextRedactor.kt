package com.example.noteApp.textreadctor

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.style.TypefaceSpan
import androidx.core.content.res.ResourcesCompat
import com.example.noteApp.R


class TextRedactor(private var context: Context) {


    fun setBold(spannable: Spannable, start: Int, count: Int) {
        val bold = Typeface.create(ResourcesCompat.getFont(context, R.font.antic), Typeface.BOLD)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannable.setSpan(
                TypefaceSpan(bold),
                start,
                count,
                Spannable.SPAN_MARK_MARK
            )
        }


    }

    fun setItalic(spannable: Spannable, start: Int, count: Int) {
        val italic =
            Typeface.create(ResourcesCompat.getFont(context, R.font.antic), Typeface.ITALIC)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannable.setSpan(
                TypefaceSpan(italic),
                start,
                count,
                Spannable.SPAN_MARK_MARK
            )
        }

    }

    fun setBoldItalic(spannable: Spannable, start: Int, count: Int) {
        val boldItalic =
            Typeface.create(ResourcesCompat.getFont(context, R.font.antic), Typeface.BOLD_ITALIC)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannable.setSpan(
                TypefaceSpan(boldItalic),
                start,
                count,
                Spannable.SPAN_MARK_MARK
            )
        }

    }
}

