package com.example.contactsblocker.model

import com.google.gson.annotations.SerializedName

data class Cities(
    val _embedded: Embedded? = null
)

data class Embedded(
    @SerializedName("city:search-results")
    val city : List<CitySearchResult>? = null
)

data class CitySearchResult(
    val matching_full_name: String? = null,
    val _links : LinksX? = null
)
data class LinksX(
    @SerializedName("city:item")
    val city_item : CityItem? = null
)

data class CityItem(
    val href: String? = null
)
