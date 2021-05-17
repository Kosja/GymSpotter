package com.example.gymspotter


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExercisesJSON(
    @JsonProperty("count")
    val count: Int,
    @JsonProperty("next")
    val next: String? = null,
    @JsonProperty("previous")
    val previous: Any?? = null,
    @JsonProperty("results")
    val results: List<ResultX>
)