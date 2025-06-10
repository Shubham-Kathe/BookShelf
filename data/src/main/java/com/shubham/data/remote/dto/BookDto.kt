package com.shubham.data.remote.dto
import com.google.gson.annotations.SerializedName

data class BookDto (
	@SerializedName("id") val id : Int,
	@SerializedName("title") var title : String,
	@SerializedName("authors") val authors : List<AuthorsDto>,
	@SerializedName("summaries") val summaries: List<String>,
	@SerializedName("subjects") val subjects : List<String>,
	@SerializedName("bookshelves") val bookshelves : List<String>,
	@SerializedName("languages") val languages : List<String>,
	@SerializedName("copyright") val copyright : Boolean,
	@SerializedName("media_type") val mediaType : String,
	@SerializedName("formats") val formats : FormatsDto,
	@SerializedName("download_count") val downloadCount : Int,
)