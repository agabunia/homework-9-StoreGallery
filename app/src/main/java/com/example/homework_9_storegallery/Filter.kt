package com.example.homework_9_storegallery


data class Filter(
    val id: Int,
    val categoryType: String,
    val image: Int = 0,
    val value: String = "REST_FILTER",
    var isActive: Boolean = false
)