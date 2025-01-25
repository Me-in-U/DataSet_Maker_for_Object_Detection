package org.tensorflow.lite.examples.objectdetection.ui.editPhoto

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import org.tensorflow.lite.examples.objectdetection.R
import org.tensorflow.lite.examples.objectdetection.ui.showTopics.client
import org.tensorflow.lite.examples.objectdetection.data.ClassData
import org.tensorflow.lite.examples.objectdetection.data.NormalResponse
import org.tensorflow.lite.examples.objectdetection.data.PictureData
import org.tensorflow.lite.examples.objectdetection.data.TopicID
import org.tensorflow.lite.examples.objectdetection.services.CreateClassService
import org.tensorflow.lite.examples.objectdetection.services.GetClasses
import org.tensorflow.lite.examples.objectdetection.services.UploadPicture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.OutputStream

class MakePhoto : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var spinner: Spinner
    private lateinit var imageView: ImageView
    private lateinit var rectView: CordinateBox
    private lateinit var textViewCoordinates: TextView
    private lateinit var resizedBitmap : Bitmap
    private lateinit var checkCordi: Button
    private lateinit var uploadButton: Button
    private lateinit var takePhotoButton: Button
    private lateinit var newObjectName: EditText
    private lateinit var addClassButton: Button
    private var topicID : Int = -1
    private var topic : String = "None"
    private var isPhotoTaken: Boolean = false // 사진 촬영 여부를 추적하는 플래그
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_photo)
        topicID = intent.getIntExtra("topicID", -1)
        topic = intent.getStringExtra("topic").toString()

        getClasses(topicID)

        imageView = findViewById(R.id.iv_captured_photo)
        rectView = findViewById(R.id.iv_captured_photo)
        spinner = findViewById(R.id.spinnerObjectClasses)
        textViewCoordinates= findViewById(R.id.textView_Coordinates)


        //!클래스 추가 버튼
        newObjectName = findViewById(R.id.newObjectName)
        addClassButton = findViewById(R.id.add_class)
        addClassButton.setOnClickListener {
            addClass(topicID, newObjectName.text.toString())
        }
        //! 사진 찍기 버튼
        takePhotoButton= findViewById(R.id.button_take_photo)
        takePhotoButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
        //! 업로드 버튼
        uploadButton = findViewById(R.id.button_upload)
        uploadButton.setOnClickListener {
            // 선택된 항목이 없으면
            if (spinner.selectedItemPosition < 0 || spinner.adapter.count == 0) {
                Toast.makeText(this, "Class를 선택하세요.", Toast.LENGTH_SHORT).show()
            }// 사진이 찍히지 않았을 경우
            else if (!isPhotoTaken) {
                Toast.makeText(this, "사진을 먼저 찍어주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                uploadPictureData()
                saveBitmapToGallery(this, resizedBitmap, topic+'_'+topicID)
                finish()
            }
        }
        //! 좌표확인 버튼
        checkCordi= findViewById(R.id.check_Cordinates)
        checkCordi.setOnClickListener {
            updataCordinates()
        }
        //! 초기 상태에서는 버튼을 숨깁니다.
        uploadButton.visibility = View.INVISIBLE
        checkCordi.visibility = View.INVISIBLE
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // @사진이 찍혔을 경우
            val imageBitmap = data?.extras?.get("data") as Bitmap
            resizeAndCropImage(imageBitmap)
            imageView.setImageBitmap(resizedBitmap)

            // @사진이 찍혔으므로 버튼을 활성화하고 표시합니다.
            isPhotoTaken = true
            uploadButton.visibility = View.VISIBLE
            checkCordi.visibility = View.VISIBLE
        }
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    private fun uploadPictureData() {
        val selectedClass = spinner.selectedItem.toString()
        val scaleX = 416f / imageView.width
        val scaleY = 416f / imageView.height
        val rect = rectView.getCurrentRect()
        val x1 :Int = (rect.left* scaleX).toInt()
        val y1 :Int = (rect.top* scaleY).toInt()
        val x2 :Int= (rect.right* scaleX).toInt()
        val y2 :Int= (rect.bottom* scaleY).toInt()
        val blob : String  = bitmapToBase64(resizedBitmap)
        Log.d("TEST", selectedClass)
        val pictureData : PictureData = PictureData(topicID, blob, x1, y1, x2, y2, selectedClass)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://jun3021306.iptime.org:8080/makeData/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // OkHttp 클라이언트 추가
            .build()
        val service = retrofit.create(UploadPicture::class.java)
        val call = service.uploadPicture(pictureData)
        call.enqueue(object : Callback<NormalResponse> {
            override fun onResponse(call: Call<NormalResponse>, response: Response<NormalResponse>) {
                if (response.isSuccessful&& response.body() != null) {
                    val returnResponse = response.body()!!
                    val message :String= returnResponse.message
                    Toast.makeText(this@MakePhoto, "업로드 성공", Toast.LENGTH_LONG).show()
                    Log.d("TEST", message)
                } else {Toast.makeText(this@MakePhoto, "서버 연결 실패", Toast.LENGTH_LONG).show()
                    Log.d("TEST", "서버 연결 실패")
                }
            }

            override fun onFailure(call: Call<NormalResponse>, t: Throwable) {
                Log.d("Test", "실패")
            }
        })
    }
    private fun updataCordinates(){
        // 사진이 찍히지 않았을 경우 좌표 업데이트를 수행하지 않습니다.
        if (!isPhotoTaken) {
            Toast.makeText(this, "사진을 먼저 찍어주세요.", Toast.LENGTH_SHORT).show()
            return
        }
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
                    }
                    Toast.makeText(this@MakePhoto, "Class 추가 완료", Toast.LENGTH_LONG).show()
                    Log.d("TEST", message)
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
                    spinner.adapter = adapter
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



    private fun resizeAndCropImage(original: Bitmap) {
// 1:1 비율로 크롭
        val minSize = Math.min(original.width, original.height)
        val croppedBitmap = Bitmap.createBitmap(original, 0, 0, minSize, minSize)
        resizedBitmap = Bitmap.createScaledBitmap(croppedBitmap, 416, 416, false)
    }
    fun saveBitmapToGallery(context: Context, bitmap: Bitmap, fileName: String) {
        // 저장될 이미지에 대한 메타데이터 설정
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName) // 파일명
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg") // 이미지 타입
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/tfImages") // 저장될 경로 (API 29 이상)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        // MediaStore에 이미지를 저장
        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        // 파일 저장
        uri?.let {
            var outputStream: OutputStream? = null
            try {
                outputStream = resolver.openOutputStream(it)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // Bitmap을 JPEG 형식으로 변환
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                outputStream?.close()
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(it, contentValues, null, null)
                }
            }
        }
    }
}