package io.github.jwgibanez.contacts.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.jwgibanez.contacts.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user ORDER BY name ASC")
    fun all(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun find(id: Int): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Query("DELETE FROM user WHERE id = :id")
    fun delete(id: String)
}