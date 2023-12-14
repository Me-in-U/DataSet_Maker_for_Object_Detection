package org.tensorflow.lite.examples.objectdetection.services

import org.tensorflow.lite.examples.objectdetection.data.IDResponse
import org.tensorflow.lite.examples.objectdetection.data.TopicID
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GetPhotosIDs {
    @POST("getData/getPhotosIDs.php")
    fun getPhotosIDs(@Body topicID: TopicID): Call<IDResponse>
}