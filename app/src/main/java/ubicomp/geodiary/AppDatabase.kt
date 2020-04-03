package ubicomp.geodiary

import android.content.Context
import androidx.room.*

@Database(
    entities = [EntryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun EntryDAO(): EntryDAO

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "entry-list.db")
            .build()
    }
}