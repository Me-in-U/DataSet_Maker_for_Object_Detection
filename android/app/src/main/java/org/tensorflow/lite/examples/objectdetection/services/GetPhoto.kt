package org.tensorflow.lite.examples.objectdetection.services

import org.tensorflow.lite.examples.objectdetection.data.ID
import org.tensorflow.lite.examples.objectdetection.data.PhotoNCordNClassnameData
import org.tensorflow.lite.examples.objectdetection.data.TopicID
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GetPhoto {
    @POST("/getData/getPhoto.php")
    fun getPhoto(@Body id: ID): Call<PhotoNCordNClassnameData>
}