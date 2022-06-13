package com.zaky.capstone.data

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("authorSlug")
	val authorSlug: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("length")
	val length: Int? = null,

	@field:SerializedName("dateModified")
	val dateModified: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("dateAdded")
	val dateAdded: String? = null,

	@field:SerializedName("tags")
	val tags: List<String?>? = null
)
