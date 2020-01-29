package com.spencerstudios.iamnotarobot

fun getChars(): ArrayList<Char> {
    val arr = ArrayList<Char>()
    for (i in 48..122)
        when (i) {
            in 48..57, in 65..90, in 97..122 -> arr += i.toChar()
        }
    return arr
}