package ubicomp.geodiary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.*

@Entity(tableName = "entries")
data class Entry(
    @ColumnInfo(name = "location") var location: String?,
    @ColumnInfo(name = "entrytext") var entrytext: String,
    //@ColumnInfo(name = "time") var time: Long?,
    @PrimaryKey(autoGenerate = true) var uid: Int?
)

@Dao
interface EntryDao {
    @Transaction @Insert
    fun insert(entry: Entry): Long

    @Query("DELETE FROM entries WHERE uid = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM entries")
    fun getEntries(): List<Entry>
}