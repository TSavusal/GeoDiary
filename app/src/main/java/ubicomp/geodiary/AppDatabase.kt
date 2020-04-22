package ubicomp.geodiary

import androidx.room.*

@Database(entities = arrayOf(EntryEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
}