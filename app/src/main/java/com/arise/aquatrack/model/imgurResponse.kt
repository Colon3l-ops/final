package com.arise.aquatrack.model

data class ImgurResponse(
    val data: ImgurData,
    val success: Boolean,
    val status: Int
)

data class ImgurData(
    val link: String
)
data class ImgurUploadResponse(
    val data: ImgurImageData?,
    val success: Boolean,
    val status: Int
)

data class ImgurImageData(
    val id: String,
    val title: String?,
    val description: String?,
    val datetime: Long,
    val type: String,
    val animated: Boolean,
    val width: Int,
    val height: Int,
    val size: Int,
    val views: Int,
    val bandwidth: Long,
    val section: String?,
    val link: String, // This is the image URL you need
    val gifv: String?,
    val mp4: String?,
    val mp4_size: Int,
    val looping: Boolean,
    val favorite: Boolean,
    val nsfw: String?, // Could be Boolean or String depending on API response
    val vote: String?,
    val in_gallery: Boolean
)
