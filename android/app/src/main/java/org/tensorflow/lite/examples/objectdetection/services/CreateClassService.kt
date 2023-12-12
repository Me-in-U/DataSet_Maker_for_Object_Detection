package org.tensorflow.lite.examples.objectdetection.services

import org.tensorflow.lite.examples.objectdetection.data.ClassData
import org.tensorflow.lite.examples.objectdetection.data.NormalResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateClassService {
    @POST("makeClass.php")
    fun makeTopic(
        @Body classData: ClassData
    ): Call<NormalResponse>
}