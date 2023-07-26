package com.example.tmdbborrador.util

class Constants {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POPULAR_ENDPOINT = "movie/popular"
        const val UPCOMING_ENDPOINT = "movie/upcoming"
        const val TOP_RATED_ENDPOINT = "movie/top_rated"
        const val MOVIE_DETAILS_ENDPOINT = "movie/{movie_id}"
        const val MOVIE_VIDEOS_ENDPOINT = "movie/{movie_id}/videos"
        const val MOVIE_CREDITS_ENDPOINT = "movie/{movie_id}/credits"
        const val PEOPLE_DETAIL_ENDPOINT = "person/{person_id}"
        const val PEOPLE_IMAGE_ENDPOINT = "person/{person_id}/images"
        const val PEOPLE_MOVIE_CREDITS = "person/{person_id}/movie_credits"
        const val API_KEY = "2c5a2488dd5ee2d47c70839c39ec4920"
    }
}