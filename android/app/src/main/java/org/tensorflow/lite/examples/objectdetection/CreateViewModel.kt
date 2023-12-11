package org.tensorflow.lite.examples.objectdetection

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.tensorflow.lite.examples.objectdetection.data.Item
import org.tensorflow.lite.examples.objectdetection.services.GetTopics
import org.tensorflow.lite.examples.objectdetection.services.Username

class CreateViewModel : ViewModel() {
    val items = MutableLiveData<List<Item>>()

    fun fetchDataFromServer(username: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jun3021306.iptime.org:8080/getData/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GetTopics::class.java)
        val usernameObj = Username(username)
        val call = service.getTopics(usernameObj)

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
            }
        })
    }
}
