package com.example.gymspotter


import com.fasterxml.jackson.annotation.JsonProperty

data class Categories(
    @JsonProperty("count")
    val count: Int,
    @JsonProperty("next")
    val next: Any?,
    @JsonProperty("previous")
    val previous: Any?,
    @JsonProperty("results")
    val results: List<Result>
)