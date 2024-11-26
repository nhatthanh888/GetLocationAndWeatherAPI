package com.example.nhatthanh.model

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("long")
    val long: Double
)