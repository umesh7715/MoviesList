package com.andromesh.movieslist.movies.data

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "movie_detail")
data class MovieDetail(

    @NotNull
    @field:SerializedName("adult")
    val adult: Boolean,

    @NotNull
    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,


    @NotNull
    @field:SerializedName("original_title")
    val original_title: String,

    @Nullable
    @field:SerializedName("overview")
    val overview: String,

    @Nullable
    @field:SerializedName("poster_path")
    var poster_path: String,

    @NotNull
    @field:SerializedName("title")
    val title: String,

    @Nullable
    @field:SerializedName("tagline")
    val tagline: String,

    @NotNull
    @field:SerializedName("vote_average")
    val vote_average: Double,

    @NotNull
    @field:SerializedName("runtime")
    val runtime: Int,


    @Nullable
    var isFavorite: Boolean,

    @NotNull
    @field:SerializedName("production_companies")
    var production_companies: List<ProductionCompanies>,

    @NotNull
    @field:SerializedName("genres")
    var genres: List<Genre>


) {

    fun convertTime(): String {

        return "$runtime Minutes"
    }

    fun convertGenres(): String {

        var genreString = ""
        for (genre in genres) {
            genreString += genre.name + ","
        }

        return genreString.substring(0, genreString.lastIndex)

    }

    fun convertProductions(): String {

        var productionString = ""
        for (production in production_companies) {
            productionString += production.name + ","
        }

        return if (productionString.isEmpty()) "" else productionString.substring(
            0, productionString.lastIndex
        )

    }

    companion object {

        fun getPath(path: String): String {
            return "https://image.tmdb.org/t/p/w500$path"
        }


    }
}