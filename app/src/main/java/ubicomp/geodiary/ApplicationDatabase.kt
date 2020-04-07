package ubicomp.geodiary

@Database(entities = [Entry::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase {
    abstract fun entryDao() : EntryDAO
}