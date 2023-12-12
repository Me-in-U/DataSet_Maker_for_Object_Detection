package org.tensorflow.lite.examples.objectdetection.services

import org.tensorflow.lite.examples.objectdetection.data.Item
import org.tensorflow.lite.examples.objectdetection.data.UsernameData
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body

interface GetTopics {
    @POST("getTopics.php")
    fun getTopics(@Body usernameData: UsernameData): Call<List<Item>>
}