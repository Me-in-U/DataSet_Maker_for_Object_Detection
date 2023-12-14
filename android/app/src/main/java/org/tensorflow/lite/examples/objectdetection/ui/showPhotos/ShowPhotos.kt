package org.tensorflow.lite.examples.objectdetection.ui.showPhotos

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.tensorflow.lite.examples.objectdetection.R
import org.tensorflow.lite.examples.objectdetection.data.ID
import org.tensorflow.lite.examples.objectdetection.data.IDResponse
import org.tensorflow.lite.examples.objectdetection.data.PhotoAndClassData
import org.tensorflow.lite.examples.objectdetection.data.TopicID
import org.tensorflow.lite.examples.objectdetection.fragments.client
import org.tensorflow.lite.examples.objectdetection.services.GetPhoto
import org.tensorflow.lite.examples.objectdetection.services.GetPhotosIDs
import org.tensorflow.lite.examples.objectdetection.ui.editPhoto.MakePhoto
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
class ShowPhotos : AppCompatActivity() {
    private var topicID : Int = -1
    private var topic : String = "None"
    private lateinit var addPhotoButton: ImageButton
    private lateinit var topicText:TextView
    private var ids : List<Int> = listOf()
    private lateinit var topicImageView : ImageView
    private lateinit var classNameTextView : TextView
    private lateinit var previousImageButton : ImageButton
    private lateinit var nextImageButton : ImageButton
    private var currentImageIndex = 0
    private lateinit var imagesCounter : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_photos)
        topicID = intent.getIntExtra("topicID", -1)
        topic = intent.getStringExtra("topic").toString()

        topicText = findViewById(R.id.item_topic)
        topicText.text = topic
        topicImageView = findViewById(R.id.topicImageView)
        classNameTextView = findViewById(R.id.classNameTextView)
        imagesCounter = findViewById(R.id.imagesCounter)

        addPhotoButton = findViewById(R.id.add_photo_button)
        addPhotoButton.setOnClickListener {
            val intent = Intent(this, MakePhoto::class.java)
            intent.putExtra("topicID", topicID) // topicID 값을 Intent에 넣음
            intent.putExtra("topic", topic) // topic 값을 Intent에 넣음
            this.startActivity(intent)
        }
        getTopicImageIDs()

        previousImageButton = findViewById(R.id.previous_image_button)
        previousImageButton.setOnClickListener(){
            if(currentImageIndex!=0) {
                currentImageIndex--
                loadPhoto(ids[currentImageIndex])
            }else{
                Toast.makeText(this@ShowPhotos,"이전 사진이 더 없습니다",Toast.LENGTH_LONG).show()
            }

        }
        nextImageButton = findViewById(R.id.next_imag_buttone)
        nextImageButton.setOnClickListener(){
            if(currentImageIndex<ids.size-1) {
                currentImageIndex++
                loadPhoto(ids[currentImageIndex])
            }else{
                Toast.makeText(this@ShowPhotos,"이후 사진이 더 없습니다",Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        getTopicImageIDs()
    }
    private fun getTopicImageIDs() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jun3021306.iptime.org:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // OkHttp 클라이언트 추가
            .build()

        val service = retrofit.create(GetPhotosIDs::class.java)
        val call = service.getPhotosIDs(TopicID(topicID))
        call.enqueue(object : Callback<IDResponse> {
            override fun onResponse(call: Call<IDResponse>, response: Response<IDResponse>) {
                if (response.isSuccessful&& response.body() != null) {
                    val returnResponse = response.body()!!
                    val returnIDs : List<Int> = returnResponse.idList
                    if (returnResponse.success) {
                        ids = returnIDs
                        if(ids.isNotEmpty()){
                            loadPhoto(ids[0])
                        }
                    }
                } else {
                    Log.d("TEST", "서버 연결 실패")
                }
            }

            override fun onFailure(call: Call<IDResponse>, t: Throwable) {
                Log.d("Test", "실패")
                return
            }
        })
    }

    private fun loadPhoto(id:Int){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jun3021306.iptime.org:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // OkHttp 클라이언트 추가
            .build()
        val service = retrofit.create(GetPhoto::class.java)
        val call = service.getPhoto(ID(id))
        call.enqueue(object : Callback<PhotoAndClassData> {
            override fun onResponse(call: Call<PhotoAndClassData>, response: Response<PhotoAndClassData>) {
                if (response.isSuccessful&& response.body() != null) {
                    val returnResponse = response.body()!!
                    val photoBase64 = returnResponse.photo
                    val classname = returnResponse.className
                    // Base64 문자열을 ByteArray로 디코딩
                    val decodedString = Base64.decode(photoBase64, Base64.DEFAULT)
                    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    // 메인 스레드에서 ImageView 업데이트
                    runOnUiThread {
                        // ImageView에 Bitmap 설정
                        topicImageView.setImageBitmap(decodedByte)
                        // TextView에 classname 설정
                        classNameTextView.text = classname
                        val imageCounterText = getString(R.string.image_counter, currentImageIndex + 1, ids.size)
                        imagesCounter.text = imageCounterText
                    }
                } else {
                    Log.d("TEST", "서버 연결 실패")
                }
            }

            override fun onFailure(call: Call<PhotoAndClassData>, t: Throwable) {
                Log.d("Test", "실패")
                return
            }
        })
    }
}