package com.example.gymspotter


import com.fasterxml.jackson.annotation.JsonProperty

data class Result(
    @JsonProperty("id")
    val id: Int? = null,
    @JsonProperty("name")
    val name: String? = null
)