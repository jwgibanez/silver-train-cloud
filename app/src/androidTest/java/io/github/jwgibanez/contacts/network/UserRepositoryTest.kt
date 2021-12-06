package io.github.jwgibanez.contacts.network

import io.github.jwgibanez.contacts.data.Result
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var repository: IUserRepository

    @Before
    fun before() {
        val service = FakeUserService()
        repository = FakeUserRepository(service)
    }

    @Test
    fun fetchUsersTest() {
        var hasError: Boolean
        runBlocking {
            val fetch = repository.fetchUsers(null)
            hasError = when (fetch) {
                is Result.Success -> false
                is Result.Error -> true
                else -> true
            }
        }
        assertThat("Has no error", !hasError)
    }

}