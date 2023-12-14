package org.tensorflow.lite.examples.objectdetection.services

import org.tensorflow.lite.examples.objectdetection.data.NormalResponse
import org.tensorflow.lite.examples.objectdetection.data.PictureData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UploadPicture{
    @POST("uploadPicture.php")
    fun uploadPicture(@Body pictureData: PictureData): Call<NormalResponse>
}