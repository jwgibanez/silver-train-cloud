package io.github.jwgibanez.contacts.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.jwgibanez.contacts.data.db.AppDatabase
import io.github.jwgibanez.contacts.data.db.UserDao
import io.github.jwgibanez.contacts.data.model.User
import io.github.jwgibanez.contacts.network.FakeUserRepository
import io.github.jwgibanez.contacts.network.FakeUserService
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class ContactsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase
    private lateinit var viewModel: ContactsViewModel

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        userDao = db.userDao()
        val userRepository = FakeUserRepository(FakeUserService())
        viewModel = ContactsViewModel(db, userRepository)
    }

    @After
    @Throws(IOException::class)
    fun after() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun fetchUsers() {
        var users: List<User>? = null
        val observer0 = Observer<List<User>> {
            users = it
        }
        try {
            viewModel.users.observeForever(observer0)
            runBlocking {
                viewModel.fetchUsers(null)
            }
            sleep(100) // wait a bit for observer1 to complete
            MatcherAssert.assertThat("User array is null", users != null)
        } finally {
            viewModel.users.removeObserver(observer0)
        }
    }
}