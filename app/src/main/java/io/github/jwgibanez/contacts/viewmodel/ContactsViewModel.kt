package io.github.jwgibanez.contacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData

import android.app.Activity
import androidx.lifecycle.LiveData
import io.github.jwgibanez.contacts.data.Result
import io.github.jwgibanez.contacts.data.db.AppDatabase
import io.github.jwgibanez.contacts.data.model.User
import io.github.jwgibanez.contacts.network.IUserRepository

@HiltViewModel
class ContactsViewModel @Inject constructor(
    database: AppDatabase,
    private val repository: IUserRepository
) : ViewModel() {

    val users = database.userDao().all()

    private val _user = MutableLiveData<User?>(null)
    val user: LiveData<User?> get() = _user

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> get() = _error

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    override fun onCleared() {
        super.onCleared()
        _error.value = null
        _loading.value = false
    }

    fun fetchUsers(activity: Activity?) {
        viewModelScope.launch {
            val result = repository.fetchUsers(activity)
            if (result is Result.Error) {
                _error.postValue(result.toString())
            }
        }
    }

    fun setUser(user: User) {
        _user.postValue(user)
    }
}