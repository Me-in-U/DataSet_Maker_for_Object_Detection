package org.tensorflow.lite.examples.objectdetection.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import org.tensorflow.lite.examples.objectdetection.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.tensorflow.lite.examples.objectdetection.CreateViewModel
import org.tensorflow.lite.examples.objectdetection.data.Item
import org.tensorflow.lite.examples.objectdetection.MyAdapter
import org.tensorflow.lite.examples.objectdetection.data.UserSession
import org.tensorflow.lite.examples.objectdetection.services.GetTopics
import org.tensorflow.lite.examples.objectdetection.services.Username
import org.tensorflow.lite.examples.objectdetection.ui.login.client
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

        viewModel = ViewModelProvider(this).get(CreateViewModel::class.java)
        val username = UserSession.getUsername(requireContext()) ?: ""

        viewModel.fetchDataFromServer(username)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MyAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner, { items ->
            adapter.updateItems(items)
        })

        return view
    }
}
