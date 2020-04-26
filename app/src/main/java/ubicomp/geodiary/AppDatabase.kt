package ubicomp.geodiary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Room.*

@Database(entities = arrayOf(EntryEntity::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "entry-items.db")
            .build()
    }
}