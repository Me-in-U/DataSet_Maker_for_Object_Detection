package org.tensorflow.lite.examples.objectdetection.data

data class Item(val id : Int, val topic: String, val count: Int)
data class LoginCredentials(val username: String, val password: String)
data class LoginResponse(val success: Boolean, val message: String)
