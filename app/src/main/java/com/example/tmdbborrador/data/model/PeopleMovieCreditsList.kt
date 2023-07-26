package com.example.tmdbborrador.data.model

data class PeopleMovieCreditsList(
    val cast: List<PeopleCast>,
    val crew: List<PeopleCrew>,
    val id: Int
)