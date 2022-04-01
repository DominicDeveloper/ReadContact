package com.dominic.readingcontact

import android.graphics.Bitmap
import java.io.Serializable

class ContactMe:Serializable {
    var name:String = ""
    var number:String = ""
    var photo:Bitmap? = null

    constructor(name: String, number: String, photo: Bitmap?) {
        this.name = name
        this.number = number
        this.photo = photo
    }
    constructor()
    constructor(name: String, number: String) {
        this.name = name
        this.number = number
    }

}