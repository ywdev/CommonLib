package com.ywcommon.common.demotest

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MyViewModel3(countReserved : Int) : ViewModel() {
    private val userIdLiveData = MutableLiveData<String>()

    val user : LiveData<User> = Transformations.switchMap(userIdLiveData, Function { input ->
        Repository.getUser(input)
    })

    fun getUser(userId: String){
        userIdLiveData.value = userId
    }

    object Repository{
        fun getUser(userId : String) : LiveData<User> {
            val liveData = MutableLiveData<User>()
            return when (userId) {
                "0" -> {
                    liveData.value = User("yao","wang", 30)
                    return liveData
                }
                "1" -> {
                    liveData.value = User("zhang", "fei", 31)
                    return liveData
                }
                "2" -> {
                    liveData.value = User("liu", "bei", 32)
                    return liveData
                }
                else -> liveData
            }
        }
    }
}