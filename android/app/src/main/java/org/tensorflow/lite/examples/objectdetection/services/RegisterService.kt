package org.tensorflow.lite.examples.objectdetection.services

import org.tensorflow.lite.examples.objectdetection.data.LoginCredentials
import org.tensorflow.lite.examples.objectdetection.data.NormalResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface RegisterService {
    @POST("register.php") // PHP 로그인 엔드포인트
    fun register(@Body credentials: LoginCredentials): Call<NormalResponse>
}
