package io.github.jwgibanez.contacts.network

import android.app.Activity
import io.github.jwgibanez.contacts.data.Result

interface IUserRepository {

    suspend fun fetchUsers(activity: Activity?) : Result<Boolean>

    fun isNetworkConnected(activity: Activity, connected : () -> Unit) : Result<Boolean>
}