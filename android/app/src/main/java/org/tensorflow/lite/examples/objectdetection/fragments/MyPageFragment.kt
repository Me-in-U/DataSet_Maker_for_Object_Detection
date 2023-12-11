package org.tensorflow.lite.examples.objectdetection.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentMyPageBinding
import org.tensorflow.lite.examples.objectdetection.data.UserSession
import org.tensorflow.lite.examples.objectdetection.ui.login.LoginActivity

class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayUsername()
        binding.logoutButton.setOnClickListener {
            UserSession.clearUsername(requireContext())
            // 로그아웃 후 LoginActivity로 이동합니다.
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Back stack을 클리어합니다.
            startActivity(intent)
            activity?.finish() // 현재 액티비티를 종료합니다.
        }
    }

    private fun displayUsername() {
        val username = UserSession.getUsername(requireContext())
        binding.usernameTextView.text = username ?: "Guest"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}