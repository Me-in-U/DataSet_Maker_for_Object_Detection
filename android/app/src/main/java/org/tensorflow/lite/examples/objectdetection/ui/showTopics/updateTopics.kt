package org.tensorflow.lite.examples.objectdetection.ui.showTopics

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.tensorflow.lite.examples.objectdetection.data.Item
import org.tensorflow.lite.examples.objectdetection.data.UsernameData
import org.tensorflow.lite.examples.objectdetection.services.GetTopics

val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

class CreateViewModel : ViewModel() {
    val items = MutableLiveData<List<Item>>()

    fun fetchDataFromServer(username: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jun3021306.iptime.org:8080/getData/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // OkHttp 클라이언트 추가
            .build()

        val service = retrofit.create(GetTopics::class.java)
        val call = service.getTopics(UsernameData(username))
        Log.d("Test", "주제 가져오기")
        Log.d("Test", username)
        call.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    items.postValue(response.body() ?: emptyList())
                    Log.d("Test", "요청 성공")
                } else {
                    // 요청 실패 처리
                    items.postValue(emptyList())
                    Log.d("Test", "요청 실패")
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                // 네트워크 요청 실패 처리
                items.postValue(emptyList())
                Log.d("Test", "주제 가져오기 onFailure")
            }
        })
    }
}
