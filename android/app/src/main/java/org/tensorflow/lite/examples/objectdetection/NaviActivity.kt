package org.tensorflow.lite.examples.objectdetection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityNaviBinding
import org.tensorflow.lite.examples.objectdetection.fragments.CameraFragment
import org.tensorflow.lite.examples.objectdetection.fragments.CreateFragment
import org.tensorflow.lite.examples.objectdetection.fragments.MyPageFragment


private const val TAG_CAMERA = "detect_cam_fragment"
private const val TAG_HOME = "home_fragment"
private const val TAG_MY_PAGE = "my_page_fragment"

class NaviActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //! 처음 켜면 카메라 화면
        setFragment(TAG_CAMERA, CameraFragment())
        //! 이후 하단 바에서 선택하면 화면 바뀜
        binding.navigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.cameraFragment -> setFragment(TAG_CAMERA, CameraFragment())
                R.id.createFragment -> setFragment(TAG_HOME, CreateFragment())
                R.id.myPageFragment -> setFragment(TAG_MY_PAGE, MyPageFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        fragTransaction.replace(R.id.mainFrameLayout, fragment, tag)

        val camera = manager.findFragmentByTag(TAG_CAMERA)
        val home = manager.findFragmentByTag(TAG_HOME)
        val myPage = manager.findFragmentByTag(TAG_MY_PAGE)

        when {
            tag == TAG_CAMERA && camera!=null -> {
                fragTransaction.show(camera)
            }
            tag == TAG_HOME && home != null -> {
                fragTransaction.show(home)
            }
            tag == TAG_MY_PAGE && myPage != null -> {
                fragTransaction.show(myPage)
            }
        }
        fragTransaction.commitAllowingStateLoss()
    }
}