package io.github.susonwaiba.kotlinfirebase

/**
 * Created by suson on 12/27/2017.
 */
class Hero (
        val id: String,
        val name: String,
        val rating: Int
) {
    constructor(): this("","",0) {

    }
}