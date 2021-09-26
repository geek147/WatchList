package com.envious.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WatchListResponse(
    @SerializedName("Data")
    val `data`: List<Data?>? = null,
    @SerializedName("HasWarning")
    val hasWarning: Boolean? = null,
    @SerializedName("Message")
    val message: String? = null,
    @SerializedName("Type")
    val type: Int? = null
) {
    @Keep
    data class Data(
        @SerializedName("CoinInfo")
        val coinInfo: CoinInfo? = null,
        @SerializedName("RAW")
        val rAW: RAW? = null
    ) {
        @Keep
        data class CoinInfo(
            @SerializedName("FullName")
            val fullName: String? = null,
            @SerializedName("Id")
            val id: String? = null,
            @SerializedName("Name")
            val name: String? = null,
        )

        @Keep
        data class RAW(
            @SerializedName("USD")
            val uSD: USD? = null
        ) {
            @Keep
            data class USD(
                @SerializedName("CHANGE24HOUR")
                val cHANGE24HOUR: Double? = null,
                @SerializedName("PRICE")
                val pRICE: Double? = null
            )
        }
    }
}
