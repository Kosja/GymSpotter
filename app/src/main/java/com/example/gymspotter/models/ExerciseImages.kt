package com.example.gymspotter

import com.fasterxml.jackson.annotation.JsonProperty

data class ExerciseImages(
    @JsonProperty("count")
    val count: Int? = null,
    @JsonProperty("next")
    val next: Any? = null,
    @JsonProperty("previous")
    val previous: Any? = null,
    @JsonProperty("results")
    val exerciseImageResults: List<ExerciseImageResult>
)