package com.example.gymspotter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class Muscles(var id: Int = 0, var name: String? = null) {
    override fun toString(): String {
        return "$name"
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class MusclesJsonObject(var results: MutableList<Muscles>? = null)