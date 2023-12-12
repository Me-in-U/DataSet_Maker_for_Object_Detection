package org.tensorflow.lite.examples.objectdetection.services

import org.tensorflow.lite.examples.objectdetection.data.Item
import org.tensorflow.lite.examples.objectdetection.data.LoginCredentials
import org.tensorflow.lite.examples.objectdetection.data.NormalResponse
import org.tensorflow.lite.examples.objectdetection.data.TopicData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateTopicService {
    @POST("makeTopic.php")
    fun makeTopic(
        @Body topicData: TopicData
    ): Call<NormalResponse>
}