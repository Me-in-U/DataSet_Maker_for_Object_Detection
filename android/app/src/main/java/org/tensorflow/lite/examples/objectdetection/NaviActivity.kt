package org.tensorflow.lite.examples.objectdetection

import android.os.Bundle
import android.widget.Toast
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
    private var time : Long= 0;
    private var currentTabIndex: Int = 0 // 현재 탭 인덱스
    override fun onBackPressed() {
        if(time + 3000 > System.currentTimeMillis()){
            super.onBackPressed()
            finish()
        }else{
            Toast.makeText(applicationContext, "'뒤로'버튼을 한번 더 누르면 종료됩니다.",
                Toast.LENGTH_SHORT).show()
        }
        time = System.currentTimeMillis()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //! 처음 켜면 카메라 화면
        setFragment(TAG_CAMERA, CameraFragment(),0)
        //! 이후 하단 바에서 선택하면 화면 바뀜
        binding.navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.cameraFragment -> setFragment(TAG_CAMERA, CameraFragment(), 0)
                R.id.createFragment -> setFragment(TAG_HOME, CreateFragment(), 1)
                R.id.myPageFragment -> setFragment(TAG_MY_PAGE, MyPageFragment(), 2)
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment, tabIndex: Int) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        // 이전 탭과 현재 탭에 따라 애니메이션 결정
        if (tabIndex > currentTabIndex) {
            // 오른쪽에서 왼쪽으로 슬라이드
            fragTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
        } else if (tabIndex < currentTabIndex) {
            // 왼쪽에서 오른쪽으로 슬라이드
            fragTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        fragTransaction.replace(R.id.mainFrameLayout, fragment, tag)
        fragTransaction.commitAllowingStateLoss()

        // 현재 탭 인덱스 업데이트
        currentTabIndex = tabIndex
    }
}