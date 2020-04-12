package ubicomp.geodiary

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*


@Entity(tableName = "entry_table")
data class EntryEntity(
    @NonNull
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "address") var address: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "entry_title") var entry_title: String,
    @ColumnInfo(name = "entry_text") var entry_text: String
)

@Dao
interface EntryDao {
    @Transaction @Insert
    fun insert(entry: EntryEntity): Long

    @Query("DELETE FROM entry_table WHERE 'uid' == :id")
    fun delete(id: Int)

    @Query("SELECT * FROM entry_table")
    fun getEntries(): List<EntryEntity>

    @Insert
    fun insert(entry: EntryEntity?)

    @Query("DELETE FROM entry_table")
    fun deleteAll()

    @Query("SELECT * from entry_table ORDER BY entry_text ASC")
    open fun getAllEntries(): MutableLiveData<List<EntryEntity>>

}