package com.example.gymspotter


import com.fasterxml.jackson.annotation.JsonProperty

data class Result(
    @JsonProperty("category")
    val category: Int,
    @JsonProperty("creation_date")
    val creationDate: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("equipment")
    val equipment: List<Int>,
    @JsonProperty("exercise_base")
    val exerciseBase: Int,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("language")
    val language: Int,
    @JsonProperty("license")
    val license: Int,
    @JsonProperty("license_author")
    val licenseAuthor: String,
    @JsonProperty("muscles")
    val muscles: List<Int>,
    @JsonProperty("muscles_secondary")
    val musclesSecondary: List<Int>,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("status")
    val status: String,
    @JsonProperty("uuid")
    val uuid: String,
    @JsonProperty("variations")
    val variations: List<Int>
)