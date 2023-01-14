package com.example.primeraprueba
import java.util.*

data class UsrModel (
    var id: Int=getAutoId(),
    var usuario: String="",
    var email: String=""
){
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}