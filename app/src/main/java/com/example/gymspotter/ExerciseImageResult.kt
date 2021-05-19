package com.example.gymspotter


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
data class ExerciseImageResult(
    @JsonProperty("image")
    val image: String? = null
) {
    override fun toString(): String {
        return "$image"
    }
}