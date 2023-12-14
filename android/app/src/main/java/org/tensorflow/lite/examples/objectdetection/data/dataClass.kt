package org.tensorflow.lite.examples.objectdetection.data

import org.tensorflow.lite.examples.objectdetection.ui.showPhotos.ShowPhotos

data class Item(val id : Int, val topic: String, val count: Int)
data class LoginCredentials(val username: String, val password: String)
data class NormalResponse(val success: Boolean, val message: String)

data class TopicData(val username: String, val topic: String)
data class UsernameData(val username: String)

data class ClassData(val topicId : Int, val className: String )
data class TopicID(val topicId : Int)
data class ID(val id : Int)

data class PictureData(val topicId: Int, val picture: String , val x1 : Int, val y1 : Int, val x2 : Int, val y2 : Int, val className: String)

data class PhotoNCordNClassnameData(val photo: String,val x1: Int,val y1: Int, val x2: Int,val y2: Int,val className: String)

data class IDResponse(val success: Boolean, val idList: List<Int>)