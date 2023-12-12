package org.tensorflow.lite.examples.objectdetection.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import org.tensorflow.lite.examples.objectdetection.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.tensorflow.lite.examples.objectdetection.CreateViewModel
import org.tensorflow.lite.examples.objectdetection.MyAdapter
import org.tensorflow.lite.examples.objectdetection.data.NormalResponse
import org.tensorflow.lite.examples.objectdetection.data.TopicData
import org.tensorflow.lite.examples.objectdetection.data.UserSession
import org.tensorflow.lite.examples.objectdetection.services.CreateTopicService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()
class CreateFragment : Fragment() {
    private lateinit var viewModel: CreateViewModel
    private lateinit var adapter: MyAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)
        val username = UserSession.getUsername(requireContext()) ?: ""

        viewModel = ViewModelProvider(this).get(CreateViewModel::class.java)

        viewModel.fetchDataFromServer(username)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MyAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner, { items ->
            adapter.updateItems(items)
        })

        val editTextNewTopic: EditText = view.findViewById(R.id.editText_NewTopic)
        val buttonAddTopic :Button = view.findViewById(R.id.button_add_topic)
        buttonAddTopic.setOnClickListener {
            val newTopic = editTextNewTopic.text.toString()
            makeTopic(username,newTopic)
        }
        return view
    }
    private fun makeTopic(username: String, newTopic:String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jun3021306.iptime.org:8080/makeData/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // OkHttp 클라이언트 추가
            .build()

        val service = retrofit.create(CreateTopicService::class.java)
        val call = service.makeTopic(TopicData(username,newTopic))
        Log.d("TEST", newTopic)
        call.enqueue(object : Callback<NormalResponse> {
            override fun onResponse(call: Call<NormalResponse>, response: Response<NormalResponse>) {
                if (response.isSuccessful&& response.body() != null) {
                    val returnResponse = response.body()!!
                    val message :String= returnResponse.message
                    if (returnResponse.success) {
                        viewModel.fetchDataFromServer(username)
                        Log.d("TEST", message)
                    } else {
                        // 실패한 경우, 서버의 메시지를 사용
                        Log.d("TEST", message)
                    }
                } else {
                    Log.d("TEST", "서버 연결 실패")
                }
            }

            override fun onFailure(call: Call<NormalResponse>, t: Throwable) {
                Log.d("Test", "실패")
            }
        })
    }
}
