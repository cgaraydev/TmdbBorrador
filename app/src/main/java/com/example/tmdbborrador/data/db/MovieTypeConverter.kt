package com.example.tmdbborrador.data.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.tmdbborrador.data.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverters
class MovieTypeConverter {

    private val gson: Gson = Gson()


    @TypeConverter
    fun fromListToString(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(string: String): List<Int> {
        return string.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromGenreListToString(genres: List<Genre>): String {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun fromStringToGenreList(string: String): List<Genre> {
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun fromProductionCompanyListToString(companies: List<ProductionCompany>): String {
        return gson.toJson(companies)
    }

    @TypeConverter
    fun fromStringToProductionCompanyList(string: String): List<ProductionCompany> {
        val type = object : TypeToken<List<ProductionCompany>>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun fromProductionCountryListToString(countries: List<ProductionCountry>): String {
        return gson.toJson(countries)
    }

    @TypeConverter
    fun fromStringToProductionCountryList(string: String): List<ProductionCountry> {
        val type = object : TypeToken<List<ProductionCountry>>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun fromSpokenLanguageListToString(languages: List<SpokenLanguage>): String {
        return gson.toJson(languages)
    }

    @TypeConverter
    fun fromStringToSpokenLanguageList(string: String): List<SpokenLanguage> {
        val type = object : TypeToken<List<SpokenLanguage>>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun fromBelongsToCollectionToString(collection: BelongsToCollection?): String {
        return gson.toJson(collection)
    }

    @TypeConverter
    fun fromStringToBelongsToCollection(string: String): BelongsToCollection? {
        val type = object : TypeToken<BelongsToCollection>() {}.type
        return gson.fromJson(string, type)
    }

}