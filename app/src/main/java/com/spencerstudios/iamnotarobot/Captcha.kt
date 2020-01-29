package com.spencerstudios.iamnotarobot

import android.content.Context
import android.graphics.*
import android.support.v4.content.res.ResourcesCompat
import java.io.CharArrayWriter
import java.util.*

class Captcha(ctx: Context, private val width: Int, private val height: Int, private val chars : ArrayList<Char>) {

    private var answer = ""
    private val wordLength = 5
    private val tf = ResourcesCompat.getFont(ctx, R.font.anonymous_pro_bold)

    fun getImage(): Bitmap {
        val paint = Paint()
        paint.apply {
            isDither = true
            shader = getGradient()
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas1 = Canvas(bitmap)
        canvas1.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        val border = Paint()
        border.apply {
            strokeWidth = 5f
            style = Paint.Style.STROKE
            color = Color.BLACK
        }
        canvas1.drawRect(0f, 0f, width.toFloat(), height.toFloat(), border)

        val r = Random(System.currentTimeMillis())
        val caw = CharArrayWriter()
        for (i in 0 until wordLength) {
            val ch = chars[r.nextInt(chars.size)]
            caw.append(ch)
            answer += ch
        }
        val data = caw.toCharArray()
        val w = width / 7
        var x = 0
        val textPaint = Paint()
        val canvas2 = Canvas(bitmap)
        for (i in data.indices) {
            x += w
            val y = height / 2 + Math.abs(r.nextInt()) % 50
            textPaint.apply {
                textSkewX = r.nextFloat() - r.nextFloat()
                color = getColor(r)
                typeface = tf
                isStrikeThruText = r.nextBoolean()
                textScaleX = r.nextFloat() * (3f - .5f) + .5f
                isDither = true
                textSize = (width / 6).toFloat()
            }
            canvas2.drawText(data, i, 1, x.toFloat(), y.toFloat(), textPaint)
        }
        return bitmap
    }

    fun getAnswer(): String {
        return answer
    }

    private fun getGradient() : LinearGradient{
        return LinearGradient(
            0f,
            0f,
            (width / this.wordLength).toFloat(),
            (height / 2).toFloat(),
            Color.WHITE,
            Color.LTGRAY,
            Shader.TileMode.MIRROR
        )
    }
}