package com.example.gymspotter


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Categories(
    @JsonProperty("results")
    val categoriesResults: List<CategoriesResult>
)