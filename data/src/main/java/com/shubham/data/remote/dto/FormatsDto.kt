package com.shubham.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FormatsDto(
    @SerializedName("application/epub+zip") var epubPlusZip: String,
    @SerializedName("image/jpeg") var coverImage: String,
    @SerializedName("text/html") var html: String?,
    @SerializedName("application/rdf+xml") var rdfPlusXml: String,
    @SerializedName("application/zip") var zipFile: String,
    @SerializedName("application/x-mobipocket-ebook") var ebook: String,
    @SerializedName("text/plain; charset=utf-8") var plainText: String?,
    @SerializedName("application/pdf") var pdf: String?
)
