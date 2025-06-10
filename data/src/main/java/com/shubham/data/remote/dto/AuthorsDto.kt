package com.shubham.data.remote.dto
import com.google.gson.annotations.SerializedName

data class AuthorsDto (
	@SerializedName("name") var name : String,
	@SerializedName("birth_year") var birthYear : Int,
	@SerializedName("death_year") var deathYear : Int
)