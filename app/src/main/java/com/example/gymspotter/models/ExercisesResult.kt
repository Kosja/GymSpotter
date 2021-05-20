package com.example.gymspotter


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExercisesResult(
    @JsonProperty("category")
    val category: Int,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("exercise_base")
    val exerciseBase: Int,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("variations")
    val variations: List<Int>
)