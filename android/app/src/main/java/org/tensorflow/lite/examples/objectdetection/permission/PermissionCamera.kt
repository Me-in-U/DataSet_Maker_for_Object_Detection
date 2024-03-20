package org.tensorflow.lite.examples.objectdetection.permission
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
object PermissionCamera {

    fun requestPermission(activity: Activity, permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(activity, permission)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
        }
    }

    fun handleRequestPermissionResult(activity: Activity, requestCode: Int, permissions: Array<out String>,
                                      grantResults: IntArray, requiredPermission: String) {
        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            // 권한 거부됨, 설정 화면으로 이동
            Toast.makeText(activity, "카메라 권한이 필요합니다. 설정에서 권한을 허용해주세요.", Toast.LENGTH_LONG).show()
            val intent = Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", activity.packageName, null)
            }
            activity.startActivity(intent)
        }
    }
}