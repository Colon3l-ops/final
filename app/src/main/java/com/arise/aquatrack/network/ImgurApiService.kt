package com.arise.aquatrack.network

import com.arise.aquatrack.model.ImgurUploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImgurService {
    @Multipart
    @POST("3/image")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Header("Authorization") clientId: String
    ): Response<ImgurUploadResponse>
}
