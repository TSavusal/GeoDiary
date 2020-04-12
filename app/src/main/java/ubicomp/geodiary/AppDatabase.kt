package ubicomp.geodiary

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

@Database(entities = [EntryEntity::class], version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun EntryDao() : EntryDao
    private var INSTANCE: AppDatabase? = null
    open fun getDatabase(context: Context): AppDatabase? {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase::class.java, "entry_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                }
            }
        }
        return INSTANCE
    }
}