package io.github.jwgibanez.contacts.network

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import io.github.jwgibanez.contacts.data.db.AppDatabase
import io.github.jwgibanez.contacts.data.model.User
import io.github.jwgibanez.contacts.R
import io.github.jwgibanez.contacts.data.Result
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserRepository(private val userService: UserService): IUserRepository {

    override suspend fun fetchUsers(activity: Activity?) : Result<Boolean> {
        return withContext(Dispatchers.IO) {
            return@withContext isNetworkConnected(
                activity!!
            ) {
                userService.getUsers().safeSubscribe(object : Observer<List<User>> {
                    override fun onSubscribe(d: Disposable) {
                        // Do nothing
                    }

                    override fun onNext(value: List<User>) {
                        AppDatabase.ioThread {
                            val db = AppDatabase.getInstance(activity)
                            db.userDao().insert(value)
                        }
                    }

                    override fun onError(e: Throwable) {
                        throw e
                    }

                    override fun onComplete() {
                        // Do nothing
                    }
                })
            }
        }
    }

    override fun isNetworkConnected(activity: Activity, connected : () -> Unit) : Result<Boolean> {
        val cm =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected) {
            try {
                connected()
                Result.Success(true)
            } catch (e: Exception) {
                Result.Error(e)
            }
        } else {
            Result.Error(Exception(activity.getString(R.string.message_no_internet_connection)))
        }
    }
}