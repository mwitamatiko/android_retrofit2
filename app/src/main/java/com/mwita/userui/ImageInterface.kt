package com.mwita.uploadfiles

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ImageInterface {

    @Multipart
    @POST("upload")
    fun uploadImageFile(@Part files: List<MultipartBody.Part>, @Part("user_id") user_id: RequestBody): Call<ResponseBody>



}