package org.tensorflow.lite.examples.objectdetection.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.tensorflow.lite.examples.objectdetection.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.tensorflow.lite.examples.objectdetection.ui.showTopics.CreateViewModel
import org.tensorflow.lite.examples.objectdetection.ui.showTopics.TopicDataRecyclerView
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
    private lateinit var adapter: TopicDataRecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var username:String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)
        username = UserSession.getUsername(requireContext()) ?: ""

        viewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        viewModel.fetchDataFromServer(username)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TopicDataRecyclerView(emptyList())
        recyclerView.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.updateItems(items)
        }

        //!주제 추가
        val editTextNewTopic: EditText = view.findViewById(R.id.editText_NewTopic)
        val buttonAddTopic :ImageButton = view.findViewById(R.id.button_add_topic)
        buttonAddTopic.setOnClickListener {
            val newTopic = editTextNewTopic.text.toString()
            // newTopic이 공백인지 확인
            if (newTopic.trim().isEmpty()) {
                // 공백일 경우 Toast 메시지 표시
                Toast.makeText(context, "주제를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 공백이 아닐 경우 makeTopic 함수 호출
                makeTopic(username, newTopic)
            }
        }
        //!Topic 리로드
        val buttonReloadTopic : ImageButton = view.findViewById(R.id.reload_topics)
        buttonReloadTopic.setOnClickListener(){
            viewModel.fetchDataFromServer(username)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchDataFromServer(username)
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
                    Toast.makeText(context, "추가되었습니다", Toast.LENGTH_LONG).show()
                    Log.d("TEST", message)
                    if (returnResponse.success) {
                        viewModel.fetchDataFromServer(username)
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
