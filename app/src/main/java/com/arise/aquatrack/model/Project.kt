package com.arise.aquatrack.model

data class Project(
    val id: String = "", // <-- Add this line
    val name: String = "",
    val clientName: String = "",
    val location: String = "",
    val status: String = "",
    val startDate: String = "",
    val endDate: String = ""
)


