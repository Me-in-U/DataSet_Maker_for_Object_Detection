package org.tensorflow.lite.examples.objectdetection.services

import org.tensorflow.lite.examples.objectdetection.data.LoginCredentials
import org.tensorflow.lite.examples.objectdetection.data.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface LoginService {
    @POST("login.php") // PHP 로그인 엔드포인트
    fun loginUser(@Body credentials: LoginCredentials): Call<LoginResponse>
}
