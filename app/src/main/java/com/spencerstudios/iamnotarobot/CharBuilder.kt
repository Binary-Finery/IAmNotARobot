package com.spencerstudios.iamnotarobot

fun getChars() : ArrayList<Char>{
    val arr = ArrayList<Char>()
    for(i in 48..122)
        when(i){
        in 48..57 -> arr.add(i.toChar())
        in 65..90 -> arr.add(i.toChar())
        in 97..122 ->arr.add(i.toChar())
    }
    return arr
}