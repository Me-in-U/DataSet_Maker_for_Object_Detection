package org.tensorflow.lite.examples.objectdetection.services

import org.tensorflow.lite.examples.objectdetection.data.TopicID
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GetClasses {
    @POST("getClasses.php")
    fun getClasses(@Body topicID: TopicID) : Call<List<String>>
}