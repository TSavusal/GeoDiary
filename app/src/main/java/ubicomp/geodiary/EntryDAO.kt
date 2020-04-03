package ubicomp.geodiary

import androidx.room.*

@Dao
interface EntryDAO {
    @Query("SELECT * FROM entryentity")
    fun getAll(): List<EntryEntity>

    @Query("SELECT * FROM entryentity WHERE title LIKE :title")
    fun findByTitle(title: String): EntryEntity

    @Insert
    fun insertAll(vararg todo: EntryEntity)

    @Delete
    fun delete(todo: EntryEntity)

    @Update
    fun updateTodo(vararg todos: EntryEntity)
}