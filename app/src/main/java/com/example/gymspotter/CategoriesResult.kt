package com.example.gymspotter


import com.fasterxml.jackson.annotation.JsonProperty

data class CategoriesResult(
    @JsonProperty("id")
    val id: Int? = null,
    @JsonProperty("name")
    val name: String? = null
)