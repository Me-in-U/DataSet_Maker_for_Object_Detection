package org.tensorflow.lite.examples.objectdetection.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import org.tensorflow.lite.examples.objectdetection.data.LoginCredentials
import org.tensorflow.lite.examples.objectdetection.data.NormalResponse
import org.tensorflow.lite.examples.objectdetection.data.LoginRepository

import org.tensorflow.lite.examples.objectdetection.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.tensorflow.lite.examples.objectdetection.services.LoginService

val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()


class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    fun login(username: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jun3021306.iptime.org:8080/user/") // 서버 URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // OkHttp 클라이언트 추가
            .build()

        val service = retrofit.create(LoginService::class.java)
        val call = service.loginUser(LoginCredentials(username, password))

        call.enqueue(object : Callback<NormalResponse> {
            override fun onResponse(call: Call<NormalResponse>, response: Response<NormalResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val returnResponse = response.body()!!
                    if (returnResponse.success) {
                        _loginResult.value = LoginResult(success = LoggedInUserView(displayName = username))
                        Log.d("TEST", "로그인 성공")
                    } else {
                        // 실패한 경우, 서버의 메시지를 사용
                        _loginResult.value = LoginResult(error = R.string.login_failed, message = returnResponse.message)
                        Log.d("TEST", "로그인 실패")
                    }
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                    Log.d("TEST", "서버 연결 실패1")
                }
            }

            override fun onFailure(call: Call<NormalResponse>, t: Throwable) {
                _loginResult.value = LoginResult(error = R.string.login_failed)
                Log.d("TEST", "서버 연결 실패2: " + t.message)
            }
        })
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}