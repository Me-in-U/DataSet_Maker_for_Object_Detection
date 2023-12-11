package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import android.graphics.Bitmap
import android.graphics.RectF
import org.tensorflow.lite.examples.objectdetection.R
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView

class MakePhoto : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var imageView: ImageView
    private lateinit var spinnerObjectClasses: Spinner
    private lateinit var rectView: ResizableRectangleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_photo)
        val topicID = intent.getIntExtra("topicID", -1)
        imageView = findViewById(R.id.iv_captured_photo)
        spinnerObjectClasses = findViewById(R.id.spinnerObjectClasses)
        rectView = findViewById(R.id.iv_captured_photo)
        val editTextObjectName: EditText = findViewById(R.id.editTextObjectName)
        val textViewCoordinates: TextView = findViewById(R.id.textView_Coordinates)
        val uploadButton: Button  = findViewById(R.id.button_upload)
        uploadButton.setOnClickListener {
            // 사각형의 좌상단(x1, y1)과    GGGGGGGGGGGGGGGGGGGGGGGGGGGGGG 우하단(x2, y2)의 좌표를 가져옵니다.
            val scaleX = 416f / imageView.width
            val scaleY = 416f / imageView.height
            val rect =rectView.getCurrentRect()
            val x1 :Int = (rect.left* scaleX).toInt()
            val y1:Int = (rect.top* scaleY).toInt()
            val x2 :Int= (rect.right* scaleX).toInt()
            val y2 :Int= (rect.bottom* scaleY).toInt()

            // TextView에 좌표를 표시합니다.
            textViewCoordinates.text = "x1: $x1, y1: $y1\nx2: $x2, y2: $y2"
        }
        val takePhotoButton: Button = findViewById(R.id.button_take_photo)
        takePhotoButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
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
