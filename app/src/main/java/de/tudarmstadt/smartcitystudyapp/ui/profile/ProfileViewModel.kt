package de.tudarmstadt.smartcitystudyapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import de.tudarmstadt.smartcitystudyapp.model.User
import de.tudarmstadt.smartcitystudyapp.services.UserService
import de.tudarmstadt.smartcitystudyapp.services.UsersAndTeamService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
    private val userService: UserService
) : ViewModel() {
    val userId = MutableLiveData<String>()
    val user = MutableLiveData<User>()

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