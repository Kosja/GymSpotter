package com.example.gymspotter


import com.fasterxml.jackson.annotation.JsonProperty

data class ExerciseByMuscle(
    @JsonProperty("count")
    val count: Int,
    @JsonProperty("next")
    val next: String,
    @JsonProperty("previous")
    val previous: Any,
    @JsonProperty("results")
    val results: List<Result>
)