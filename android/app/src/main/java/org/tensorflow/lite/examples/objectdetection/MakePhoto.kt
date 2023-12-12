package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import android.graphics.Bitmap
import android.graphics.RectF
import android.util.Log
import android.widget.ArrayAdapter
import org.tensorflow.lite.examples.objectdetection.R
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import org.tensorflow.lite.examples.objectdetection.data.ClassData
import org.tensorflow.lite.examples.objectdetection.data.NormalResponse
import org.tensorflow.lite.examples.objectdetection.data.TopicData
import org.tensorflow.lite.examples.objectdetection.data.TopicID
import org.tensorflow.lite.examples.objectdetection.services.CreateClassService
import org.tensorflow.lite.examples.objectdetection.services.CreateTopicService
import org.tensorflow.lite.examples.objectdetection.services.GetClasses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MakePhoto : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var spinnershowClasses: Spinner
    private lateinit var imageView: ImageView
    private lateinit var rectView: ResizableRectangleImageView
    private lateinit var textViewCoordinates: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_photo)
        val topicID = intent.getIntExtra("topicID", -1)
        getClasses(topicID)
        imageView = findViewById(R.id.iv_captured_photo)
        rectView = findViewById(R.id.iv_captured_photo)
        spinnershowClasses = findViewById(R.id.spinnerObjectClasses)
        textViewCoordinates= findViewById(R.id.textView_Coordinates)

        //!클래스 추가 버튼
        val newObjectName: EditText = findViewById(R.id.newObjectName)
        val addlassButton: Button  = findViewById(R.id.add_class)
        addlassButton.setOnClickListener {
            addClass(topicID, newObjectName.text.toString())
        }
        //! 사진 찍기 버튼
        val takePhotoButton: Button = findViewById(R.id.button_take_photo)
        takePhotoButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
        //! 업로드 버튼
        val uploadButton: Button  = findViewById(R.id.button_upload)
        uploadButton.setOnClickListener {
        }
        //! 좌표확인 버튼
        val checkCordi: Button  = findViewById(R.id.check_Cordinates)
        checkCordi.setOnClickListener {
            updataCordinates()
        }
    }
    private fun updataCordinates(){
        val scaleX = 416f / imageView.width
        val scaleY = 416f / imageView.height
        val rect = rectView.getCurrentRect()
        val x1 :Int = (rect.left* scaleX).toInt()
        val y1 :Int = (rect.top* scaleY).toInt()
        val x2 :Int= (rect.right* scaleX).toInt()
        val y2 :Int= (rect.bottom* scaleY).toInt()

        // TextView에 좌표를 표시합니다.
        textViewCoordinates.text = "x1: $x1, y1: $y1\nx2: $x2, y2: $y2"
    }
    private fun addClass(topicID : Int, classname : String){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jun3021306.iptime.org:8080/makeData/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(org.tensorflow.lite.examples.objectdetection.fragments.client) // OkHttp 클라이언트 추가
            .build()

        val service = retrofit.create(CreateClassService::class.java)
        val call = service.makeTopic(ClassData(topicID,classname))
        Log.d("TEST", classname)
        call.enqueue(object : Callback<NormalResponse> {
            override fun onResponse(call: Call<NormalResponse>, response: Response<NormalResponse>) {
                if (response.isSuccessful&& response.body() != null) {
                    val returnResponse = response.body()!!
                    val message :String= returnResponse.message
                    if (returnResponse.success) {
                        getClasses(topicID)
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
    private fun getClasses(topicID: Int){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jun3021306.iptime.org:8080/getData/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // OkHttp 클라이언트 추가
            .build()

        val service = retrofit.create(GetClasses::class.java)
        val call = service.getClasses(TopicID(topicID))
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    // 스피너에 데이터를 설정
                    val classesList = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(this@MakePhoto, android.R.layout.simple_spinner_item, classesList)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnershowClasses.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                // 오류 처리
            }
        })
    }
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val resizedBitmap = resizeAndCropImage(imageBitmap)
            imageView.setImageBitmap(resizedBitmap)
        }
    }

    private fun resizeAndCropImage(original: Bitmap): Bitmap {
        // 1:1 비율로 크롭
        val minSize = Math.min(original.width, original.height)
        val croppedBitmap = Bitmap.createBitmap(original, 0, 0, minSize, minSize)

        // 416x416으로 리사이즈
        return Bitmap.createScaledBitmap(croppedBitmap, 416, 416, false)
    }
}
