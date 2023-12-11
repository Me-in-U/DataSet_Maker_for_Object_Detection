package org.tensorflow.lite.examples.objectdetection.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null,
    val message: String? = null // 오류 메시지 필드 추가
)
