package de.tudarmstadt.smartcitystudyapp.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import de.tudarmstadt.smartcitystudyapp.models.UserModel
import de.tudarmstadt.smartcitystudyapp.interfaces.UserServiceInterface
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
    private val userServiceInterface: UserServiceInterface
) : ViewModel() {
    val userId = MutableLiveData<String>()
    val user = MutableLiveData<UserModel>()

    fun getUserId() = viewModelScope.launch {
        userServiceInterface.getUserID()
            .collect{
                userId.postValue(it)
            }
    }
    fun getUser(userId: String)= viewModelScope.launch {
        userServiceInterface.getUser(
            userId
        ).collect {
            user.postValue(it)
        }
    }
}