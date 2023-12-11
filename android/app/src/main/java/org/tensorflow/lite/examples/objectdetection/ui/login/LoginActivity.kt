package org.tensorflow.lite.examples.objectdetection.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import org.tensorflow.lite.examples.objectdetection.NaviActivity
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityLoginBinding
import android.util.Log
import org.tensorflow.lite.examples.objectdetection.R
import android.Manifest
import org.tensorflow.lite.examples.objectdetection.data.UserSession
import org.tensorflow.lite.examples.objectdetection.permission.PermissionCamera

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    // 권한 요청에 사용할 요청 코드
    companion object {
        const val PERMISSIONS_REQUEST_CAMERA = 1
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionCamera.handleRequestPermissionResult(this, PERMISSIONS_REQUEST_CAMERA, permissions, grantResults, Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PermissionCamera.requestPermission(this, Manifest.permission.CAMERA, PERMISSIONS_REQUEST_CAMERA)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        binding.register?.setOnClickListener {
            // NaviActivity로 이동하는 Intent 생성
            val intent = Intent(this, NaviActivity::class.java)
            startActivity(intent)
            finish() // LoginActivity를 백 스택에서 제거
        }

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error, loginResult.message)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }

    }

    override fun onResume() {
        super.onResume()
        PermissionCamera.requestPermission(this, Manifest.permission.CAMERA, PERMISSIONS_REQUEST_CAMERA)
    }
    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
        // Inside LoginActivity, after a successful login
        UserSession.saveUsername(this, displayName)
        val intent = Intent(this, NaviActivity::class.java)
        startActivity(intent)
        finish() // If you want to remove LoginActivity from the back stack
    }

    private fun showLoginFailed(@StringRes errorString: Int, message: String?) {
        val errorMessage = message ?: getString(errorString)
        Log.d("TEST", errorMessage)
        Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}