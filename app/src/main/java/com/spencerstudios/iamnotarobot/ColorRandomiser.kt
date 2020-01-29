package com.spencerstudios.iamnotarobot

import android.graphics.Color
import java.util.*

private val colors = intArrayOf(
    Color.BLUE,
    Color.BLACK,
    Color.DKGRAY,
    Color.GRAY,
    Color.MAGENTA,
    Color.GREEN,
    Color.RED,
    Color.BLACK,
    Color.CYAN,
    Color.YELLOW
)

fun getColor(r : Random) : Int{
    return colors[r.nextInt(colors.size)]
}