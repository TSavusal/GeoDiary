package ubicomp.geodiary

import androidx.room.*

@Dao
interface DiaryDao {
    @Query("SELECT * FROM entry_items")
    fun getAll(): List<EntryEntity>

    @Query("SELECT * FROM entry_items WHERE eid IN (:EntryIds)")
    fun loadAllByIds(EntryIds: IntArray): List<EntryEntity>

    @Query("SELECT * FROM entry_items WHERE address LIKE :first")
    fun findByName(first: String): EntryEntity

    @Insert
    fun insertAll(vararg entries: EntryEntity)

    @Delete
    fun delete(entry: EntryEntity)
}