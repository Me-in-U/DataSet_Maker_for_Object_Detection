package org.tensorflow.lite.examples.objectdetection.data

data class Item(val id : Int, val topic: String, val count: Int)
data class LoginCredentials(val username: String, val password: String)
data class NormalResponse(val success: Boolean, val message: String)

data class TopicData(val username: String, val topic: String)
data class UsernameData(val username: String)

data class ClassData(val topicId : Int, val className: String )
data class TopicID(val topicId : Int)