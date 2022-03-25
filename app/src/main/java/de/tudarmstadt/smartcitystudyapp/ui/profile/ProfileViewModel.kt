package de.tudarmstadt.smartcitystudyapp.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import de.tudarmstadt.smartcitystudyapp.models.UserModel
import de.tudarmstadt.smartcitystudyapp.interfaces.services.UserService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
    private val userService: UserService
) : ViewModel() {
    val userId = MutableLiveData<String>()
    val user = MutableLiveData<UserModel>()

    fun getUserId() = viewModelScope.launch {
        userService.getUserID()
            .collect{
                userId.postValue(it)
            }
    }
    fun getUser(userId: String)= viewModelScope.launch {
        userService.getUser(
            userId
        ).collect {
            user.postValue(it)
        }
    }
}