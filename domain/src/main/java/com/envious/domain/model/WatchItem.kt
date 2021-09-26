package com.envious.domain.model

data class WatchItem(
    val id: String,
    val shortName: String,
    val fullName: String,
    val price: Double,
    val changePrice24: Double
)
