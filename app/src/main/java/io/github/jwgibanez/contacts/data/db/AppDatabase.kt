package io.github.jwgibanez.contacts.data.db

import android.content.Context
import androidx.room.*
import io.github.jwgibanez.contacts.data.Converters
import io.github.jwgibanez.contacts.data.model.User
import java.util.concurrent.Executors

@Database(entities = [User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "Sample.db")
                .build()

        fun ioThread(f : () -> Unit) {
            IO_EXECUTOR.execute(f)
        }
    }
}