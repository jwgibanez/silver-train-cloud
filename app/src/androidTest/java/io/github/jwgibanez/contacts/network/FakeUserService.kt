package io.github.jwgibanez.contacts.network

import io.github.jwgibanez.contacts.TestUtil
import io.github.jwgibanez.contacts.data.model.User
import io.reactivex.Observable

class FakeUserService : UserService {

    override fun getUsers(): Observable<List<User>> {
        return Observable.just(TestUtil.getUserList())
    }
}