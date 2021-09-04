package com.ywcommon.common.demotest

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MyViewModel2(countReserved : Int) : ViewModel() {

    private val userLiveData = MutableLiveData<User>()

    val userName : LiveData<String> = Transformations.map(userLiveData) { user ->
        user.firstName + "&"+ user.lastName
    }

    fun setUser(user: User){
        userLiveData.value = user
    }


}
