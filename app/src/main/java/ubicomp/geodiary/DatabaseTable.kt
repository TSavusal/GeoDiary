package ubicomp.geodiary

//TO DO: room-importti kuntoon
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.*

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true) var uid: Int?,
    @ColumnInfo(name = "location") var location: String?,
    //@ColumnInfo(name = "time") var time: Long?,
    @ColumnInfo(name = "entrytext") var entrytext: String
)

@Dao
interface EntryDAO {
    @Transaction @Insert
    fun insert(entry: Entry)

    //TO DO: -->value: "SELECT * FROM entries"
    @Query("SELECT * FROM entries")
    fun getEntries(): List<Entry>
}