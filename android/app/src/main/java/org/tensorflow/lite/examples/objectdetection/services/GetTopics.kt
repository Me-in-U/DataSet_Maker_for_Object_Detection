package org.tensorflow.lite.examples.objectdetection.services

import org.tensorflow.lite.examples.objectdetection.data.Item
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body

interface GetTopics {
    @POST("getTopics.php")
    fun getTopics(@Body username: Username): Call<List<Item>>
}
data class Username(val username: String)