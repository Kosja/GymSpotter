package com.example.gymspotter.models


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Exercises(
    @JsonProperty("results")
    val exercisesResults: List<ExercisesResult>
)