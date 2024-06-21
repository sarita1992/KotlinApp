package com.example.viesuretest.model

import java.io.Serializable

data class BookData(
    var id: Int,
    var title: String?,
    var description: String?,
    var author: String?,
    var release_date: String?,
    var image: String?
) : Serializable
