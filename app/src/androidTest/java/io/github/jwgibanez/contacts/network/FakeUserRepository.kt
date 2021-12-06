package io.github.jwgibanez.contacts.network

import android.app.Activity
import io.github.jwgibanez.contacts.data.Result

class FakeUserRepository(private val userService: UserService) : IUserRepository {

    override suspend fun fetchUsers(activity: Activity?) : Result<Boolean> =
        run {
            userService.getUsers()
            return Result.Success(true)
        }

    override fun isNetworkConnected(
        activity: Activity,
        connected: () -> Unit
    ) : Result<Boolean> =
        run {
            connected()
            Result.Success(true)
        }
}