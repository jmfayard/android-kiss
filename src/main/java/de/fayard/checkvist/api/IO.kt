package de.fayard.checkvist.api

import timber.log.Timber

inline fun <T> T.debug(name: String) : T {
    Timber.i("$name = this")
    return this
}


inline fun <T> List<T>.debugList(name: String): List<T> {
    println("<List name=$name size=$size>")
    forEachIndexed { i, t ->
        println("$name[$i] : $t")
    }
    println("</List name=$name size=$size>")
    return this
}


