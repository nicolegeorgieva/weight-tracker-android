package com.weighttracker.network.quotes

import com.google.gson.annotations.SerializedName

data class QuotesResponse(
    @SerializedName("quotes")
    val quotes: List<String>,
)