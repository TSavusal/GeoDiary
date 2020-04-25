package ubicomp.geodiary

import androidx.room.*

@Dao
interface EntryDao {
    @Transaction @Insert
    fun insertEntry(entry: EntryEntity): Long

    @Insert
    fun insert(entry: EntryEntity?)

    @Insert
    fun insertAll(vararg entry: EntryEntity)

    @Update
    fun update(entry: EntryEntity)

    //@Delete
    //fun delete(entry: Int?)

    //@Query("DELETE FROM entry_table WHERE 'id' == :id")
    //fun delete(id: Int): EntryEntity?

    //@Query("DELETE FROM entry_table")
    //fun deleteAll()

    @Query("SELECT * FROM entry_table")
    fun getAll(): List<EntryEntity>

    @Query("SELECT * FROM entry_table ORDER BY id DESC LIMIT 1")
    fun getAllEntries(): List<EntryEntity>

    @Query("SELECT * from entry_table WHERE 'id' == :id")
    fun get(id: Int): EntryEntity?

    @Query("SELECT * FROM entry_table WHERE entry_title LIKE :title")
    fun findByTitle(title: String): List<EntryEntity>

}